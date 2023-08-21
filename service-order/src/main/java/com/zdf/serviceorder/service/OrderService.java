package com.zdf.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.constant.OrderConstant;
import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.OrderInfo;
import com.zdf.internalcommon.dto.PriceRule;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.CalculatePriceRequest;
import com.zdf.internalcommon.request.OrderRequest;
import com.zdf.internalcommon.response.DriverCarResponse;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceSearchServiceResponse;
import com.zdf.internalcommon.utils.RedisKeyUtils;
import com.zdf.serviceorder.mapper.OrderInfoMapper;
import com.zdf.serviceorder.remote.ServiceDriverUserClient;
import com.zdf.serviceorder.remote.ServiceMapClient;
import com.zdf.serviceorder.remote.ServicePriceClient;
import com.zdf.serviceorder.remote.ServiceSsePushClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderService
{
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ServicePriceClient servicePriceClient;
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;
    @Autowired
    private ServiceMapClient serviceMapClient;
    @Autowired
    private ServiceSsePushClient serviceSsePushClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 添加订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult addOrder(OrderRequest orderRequest)
    {
        //判断是否是黑名单
        if (isBlackDevice(orderRequest))
        {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getMessage());
        }
        //判断该城市是否开通该服务，是否存在计价规则
        if (!isExist(orderRequest))
        {
            return ResponseResult.fail(CommonStatusEnum.SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.SERVICE_NOT_SERVICE.getMessage(), "");
        }
        //判断是否是最新的计价规则
        String fareType = orderRequest.getFareType();
        Integer fareVersion = orderRequest.getFareVersion();
        ResponseResult<Boolean> responseResult = servicePriceClient.isNewPriceRule(fareType, fareVersion);

        if (responseResult.getCode() == CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode())
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage(), "");
        }
        if (!responseResult.getData())
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGE.getCode(), CommonStatusEnum.PRICE_RULE_CHANGE.getMessage(),"");
        }

        //判断该城市是否有司机
        String address = orderRequest.getAddress();
        ResponseResult<Boolean> availableDriverUser = serviceDriverUserClient.isAvailableDriver(address);
        if (!availableDriverUser.getData())
        {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_IS_NOT_EXIST.getCode(), CommonStatusEnum.CITY_DRIVER_IS_NOT_EXIST.getMessage(),"");
        }

        //判断用户是否有正在执行的订单
        int orderCount = ifPassengerOrderIsVaild(orderRequest.getPassengerId());
        if (orderCount > 0)
        {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getMessage());
        }
        
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        orderInfoMapper.insert(orderInfo);
        //派单
        int result = 0;
        for (int i = 0; i < 6; i++)
        {
            result = dispatchRealTimeOrder(orderInfo);
            if (result == 1)
            {
                break;
            }
            if (i == 5)
            {
                orderInfo.setOrderStatus(OrderConstant.ORDER_INVAILD);
                orderInfoMapper.updateById(orderInfo);
            }
            else
            {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result == 1 ? ResponseResult.success("1"):ResponseResult.fail("");
    }

    /**
     * 派发实时订单,派单成功返回1，失败返回0
     */
    public int dispatchRealTimeOrder(OrderInfo orderInfo)
    {
        //纬度40.05,116.32
        String depLatitude = orderInfo.getDepLatitude();
        //经度
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + ',' + depLongitude;
        ArrayList<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        ResponseResult<List<TerminalServiceResponse>> listResponseResult;
        boolean carFlag = (Boolean) false;
        int dispatchResult = 0;

        for (int index = 0; index < radiusList.size() && !carFlag; index++)
        {
            Integer radius = radiusList.get(index);
            log.info("在" + radius + "米范围内找车");
            //请求map接口，搜索车辆
            listResponseResult = serviceMapClient.aroundSearch(center, radius);
            List<TerminalServiceResponse> resultData = listResponseResult.getData();
            for (TerminalServiceResponse terminalServiceResponse : resultData) {
                String desc = terminalServiceResponse.getDesc();
                long carId = Long.parseLong(desc);
                log.info(desc);
                //根据车找到绑定的用户
                ResponseResult<DriverCarResponse> availableDriver = serviceDriverUserClient.isAvailableDriver(carId);
                DriverCarResponse driverCarResponse = availableDriver.getData();
                if (availableDriver.getCode() == CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getCode() || !orderInfo.getVehicleType().trim().equals(driverCarResponse.getVehicleType().trim())) {
                    continue;
                }
                //根据司机id查看司机是否有正在执行的订单
                Long driverId = driverCarResponse.getDriverId();
                String lockKey = (driverId + "").intern();
                RLock lock = redissonClient.getLock(lockKey);
                lock.lock();
                if (ifDriverOrderIsVaild(driverId) > 0) {
                    log.info("有正在执行的订单");
                    lock.unlock();
                    continue;
                }
                dispatchResult = 1;
                //匹配司机接单
                orderInfo.setDriverId(driverId);
                orderInfo.setDriverPhone(driverCarResponse.getDriverPhone());
                orderInfo.setCarId(carId);
                orderInfo.setReceiveOrderCarLatitude(terminalServiceResponse.getLatitude());
                orderInfo.setReceiveOrderCarLongitude(terminalServiceResponse.getLongitude());
                orderInfo.setReceiveOrderTime(LocalDateTime.now());
                orderInfo.setLicenseId(driverCarResponse.getLicenseId());
                orderInfo.setVehicleNo(driverCarResponse.getVehicleNo());
                orderInfo.setOrderStatus(OrderConstant.DRIVER_RECEIVE_ORDER);
                orderInfoMapper.updateById(orderInfo);
                //向司机推送订单消息
                JSONObject driverContent = new JSONObject();
                Long passengerId = orderInfo.getPassengerId();
                driverContent.put("orderId", orderInfo.getId());
                driverContent.put("passengerId", passengerId);
                driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                driverContent.put("departure", orderInfo.getDeparture());
                driverContent.put("depLongitude", orderInfo.getDepLongitude());
                driverContent.put("depLatitude", orderInfo.getDepLatitude());
                driverContent.put("destination", orderInfo.getDestination());
                driverContent.put("desLongitude", orderInfo.getDesLongitude());
                driverContent.put("desLatitude", orderInfo.getDesLatitude());

                serviceSsePushClient.pushContent(driverId, IdentityConstant.DRIVER_IDENTITY, driverContent.toString());

                //向乘客推送订单消息
                //查询车辆信息
                ResponseResult<Car> carResponseResult = serviceDriverUserClient.getCar(carId);
                Car car = carResponseResult.getData();
                JSONObject passengerContent = new JSONObject();
                passengerContent.put("orderId", orderInfo.getId());
                passengerContent.put("brand", car.getBrand());
                passengerContent.put("model", car.getModel());
                passengerContent.put("vehicleColor", car.getVehicleColor());
                passengerContent.put("driverId", driverId);
                passengerContent.put("driverPhone", orderInfo.getDriverPhone());
                passengerContent.put("vehicleNo", orderInfo.getVehicleNo());
                passengerContent.put("licenseId", orderInfo.getLicenseId());
                driverContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                driverContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

                serviceSsePushClient.pushContent(passengerId, IdentityConstant.PASSENGER_IDENTITY, passengerContent.toString());

                carFlag = true;
                lock.unlock();
                break;
            }

        }
        return dispatchResult;
    }
    /**
     * 检查用户是否有正在执行的订单
     * @param passengerId
     * @return
     */
    private int ifPassengerOrderIsVaild(Long passengerId)
    {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper->wrapper.eq("order_status", Optional.of(OrderConstant.ORDER_START))
                .or().eq("order_status", Optional.of(OrderConstant.DRIVER_RECEIVE_ORDER))
                .or().eq("order_status", Optional.of(OrderConstant.DRIVER_TO_PICK_UP_PASSENGER))
                .or().eq("order_status", Optional.of(OrderConstant.DRIVER_ARRIVED_DEPARTURE))
                .or().eq("order_status", Optional.of(OrderConstant.PICK_UP_PASSENGER))
                .or().eq("order_status", Optional.of(OrderConstant.PASSENGER_GET_OFF))
                .or().eq("order_status", Optional.of(OrderConstant.TO_START_PAY))
        );
        return orderInfoMapper.selectCount(queryWrapper);
    }

    /**
     * 根据司机id查看司机是否有正在执行的订单
     * @param
     * @return
     */
    private int ifDriverOrderIsVaild(Long driverId)
    {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper->wrapper.eq("order_status", Optional.of(OrderConstant.DRIVER_RECEIVE_ORDER))
                .or().eq("order_status", Optional.of(OrderConstant.DRIVER_TO_PICK_UP_PASSENGER))
                .or().eq("order_status", Optional.of(OrderConstant.DRIVER_ARRIVED_DEPARTURE))
                .or().eq("order_status", Optional.of(OrderConstant.PICK_UP_PASSENGER))
        );
        return orderInfoMapper.selectCount(queryWrapper);
    }
    /**
     * 检查设备是不是在黑名单
     * @param orderRequest
     * @return
     */
    private Boolean isBlackDevice(OrderRequest orderRequest)
    {
        String deviceCode = orderRequest.getDeviceCode();
        String blackDeviceKey = RedisKeyUtils.blackDevicePrefix + deviceCode;
        Boolean result = stringRedisTemplate.hasKey(blackDeviceKey);
        if (Boolean.TRUE.equals(result))
        {
            String value = stringRedisTemplate.opsForValue().get(blackDeviceKey);
            int i = Integer.parseInt(Objects.requireNonNull(value));
            if (i >= 2)
            {
                return true;
            }
            else
            {
                stringRedisTemplate.opsForValue().increment(blackDeviceKey);
            }
        }
        else
        {
            stringRedisTemplate.opsForValue().setIfAbsent(blackDeviceKey, "1", 1, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 检查该是否开通该服务，是否存在计价规则
     * @param orderRequest
     * @return
     */
    private Boolean isExist(OrderRequest orderRequest)
    {
        //91$veniam
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> responseResult = servicePriceClient.isExist(priceRule);
        return responseResult.getData();
    }

    public ResponseResult changeOrderStatus(OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.DRIVER_TO_PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("");
    }

    public ResponseResult arriveDeparture(OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.DRIVER_ARRIVED_DEPARTURE);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("");

    }

    public ResponseResult pickUpPassenger(OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    public ResponseResult passengerGetoff(OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Long carId = orderInfo.getCarId();

        ResponseResult<Car> carResponse = serviceDriverUserClient.getCar(carId);
        Car car = carResponse.getData();
        String tid = car.getTid();
        Long starttime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        LocalDateTime now = LocalDateTime.now();
        Long endtime = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info(starttime+"");
        log.info(tid);
        log.info(endtime + "");

        ResponseResult<TraceSearchServiceResponse> trsearchResponse = serviceMapClient.trsearch(tid, starttime, endtime);
        TraceSearchServiceResponse data = trsearchResponse.getData();
        Long driveMile = data.getDriveMile();
        Long driveTime = data.getDriveTime();

        CalculatePriceRequest calculatePriceRequest = new CalculatePriceRequest();
        calculatePriceRequest.setVehicleType(orderInfo.getVehicleType());
        calculatePriceRequest.setCityCode(orderInfo.getAddress());
        calculatePriceRequest.setDriveMile(driveMile);
        calculatePriceRequest.setDriveTime(driveTime * 60);
        ResponseResult<Double> priceResponseResult = servicePriceClient.calculatePrice(calculatePriceRequest);
        log.info(priceResponseResult.toString());
        Double price = priceResponseResult.getData();

        orderInfo.setDriveMile(driveMile);
        orderInfo.setDriveTime(driveTime);
        orderInfo.setPrice(price);
        log.info(price + "");

        orderInfo.setPassengerGetoffLongitude(orderRequest.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());
        orderInfo.setPassengerGetoffTime(now);
        orderInfo.setOrderStatus(OrderConstant.PASSENGER_GET_OFF);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    public ResponseResult startPay(@RequestBody OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfo.setOrderStatus(OrderConstant.TO_START_PAY);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    public ResponseResult pay(@RequestBody OrderRequest orderRequest)
    {
        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setOrderStatus(OrderConstant.SUCCESS_PAY);
        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("success");
    }

    public ResponseResult cancel(Long orderId, String identity)
    {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Integer orderStatus = orderInfo.getOrderStatus();
        LocalDateTime cancelTime = LocalDateTime.now();
        Integer cancelTypeCode = null;
        int cancelFlag = 1;

        if (identity.trim().equals(IdentityConstant.PASSENGER_IDENTITY.trim()))
        {
            switch (orderStatus)
            {
                case OrderConstant.ORDER_START:
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                    break;
                case OrderConstant.DRIVER_RECEIVE_ORDER:
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1)
                    {
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_ILLEGAL;
                    }
                    else
                    {
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                    }
                    break;
                case OrderConstant.DRIVER_TO_PICK_UP_PASSENGER:
                case OrderConstant.DRIVER_ARRIVED_DEPARTURE:
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_ILLEGAL;
                    break;
                default:
                    log.info("取消订单失败");
                    cancelFlag = 0;
            }
        }

        if (identity.trim().equals(IdentityConstant.DRIVER_IDENTITY.trim()))
        {
            switch (orderStatus)
            {
                case OrderConstant.DRIVER_RECEIVE_ORDER:
                case OrderConstant.DRIVER_TO_PICK_UP_PASSENGER:
                case OrderConstant.DRIVER_ARRIVED_DEPARTURE:
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1)
                    {
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_ILLEGAL;
                    }
                    else
                    {
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                    }
                    break;
                default:
                    log.info("取消订单失败");
                    cancelFlag = 0;
            }
        }

        if(cancelFlag == 0)
        {
            return ResponseResult.fail("取消订单失败");
        }

        orderInfo.setCancelTime(cancelTime);
        orderInfo.setCancelOperator(Integer.parseInt(identity));
        orderInfo.setCancelTypeCode(cancelTypeCode);
        orderInfo.setOrderStatus(OrderConstant.ORDER_CANCEL);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("1");
    }
}
