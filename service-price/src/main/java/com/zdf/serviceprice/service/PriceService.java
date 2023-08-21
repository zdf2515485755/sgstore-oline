package com.zdf.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.dto.PriceRule;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.CalculatePriceRequest;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.DirectionResponse;
import com.zdf.internalcommon.response.ForecastPriceResponse;
import com.zdf.serviceprice.mapper.PriceRuleMapper;
import com.zdf.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class PriceService
{
    @Autowired
    private ServiceMapClient serviceMapClient;
    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult<ForecastPriceResponse> forecastPrice(ForecastPriceRequest forecastPriceRequest)
    {
        String cityCode = forecastPriceRequest.getCityCode();
        String vehicleType = forecastPriceRequest.getVehicleType();
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.driving(forecastPriceRequest);
        DirectionResponse directionResponse = responseResult.getData();
        Integer distance = directionResponse.getDistance();
        Integer duration = directionResponse.getDuration();
        //读取计价规则
        QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
        priceRuleQueryWrapper.eq("city_code", cityCode);
        priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
        priceRuleQueryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
        if (priceRules.size() == 0)
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage(), "");
        }
        PriceRule priceRule = priceRules.get(0);
        log.info(priceRule.toString());
        log.info("计算价格");
        double price = getPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);
        forecastPriceResponse.setFareType(priceRule.getFareType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());
        return ResponseResult.success(forecastPriceResponse);
    }

    public ResponseResult<Double> calculatePrice(CalculatePriceRequest calculatePriceRequest)
    {
        String cityCode = calculatePriceRequest.getCityCode();
        String vehicleType = calculatePriceRequest.getVehicleType();
        //读取计价规则
        QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
        priceRuleQueryWrapper.eq("city_code", cityCode);
        priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
        priceRuleQueryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
        if (priceRules.size() == 0)
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage(), "");
        }
        PriceRule priceRule = priceRules.get(0);

        Long driveTime = calculatePriceRequest.getDriveTime();
        Long driveMile = calculatePriceRequest.getDriveMile();
        double price = getPrice(driveMile.intValue(), driveTime.intValue(), priceRule);
        log.info(price + "");

        return ResponseResult.success(price);
    }

    /**
     * 计算价格
     * @return
     */
    private double getPrice(Integer distance, Integer duration, PriceRule priceRule)
    {
        BigDecimal price = new BigDecimal(0);
        //起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price = price.add(startFareDecimal);
        //里程价
        //转换里程
        BigDecimal distanceDecimal = new BigDecimal(distance);
        BigDecimal distanceMileDecimal = distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
        //获取起步距离
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileDecimal = new BigDecimal(startMile);
        double distanceSubtract = distanceMileDecimal.subtract(startMileDecimal).doubleValue();
        Double mile = (Double) (distanceSubtract < 0 ? 0 : distanceSubtract);
        //获取每公里多少钱
        BigDecimal mileDecimal = new BigDecimal(mile);
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal = new BigDecimal(unitPricePerMile);
        //计算起步价
        BigDecimal milePrice = mileDecimal.multiply(unitPricePerMileDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        price = price.add(milePrice);
        //时长价
        BigDecimal durationDecimal = new BigDecimal(duration);
        BigDecimal durationMinuteDecimal = durationDecimal.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteDecimal = new BigDecimal(unitPricePerMinute);
        BigDecimal minutePrice = durationMinuteDecimal.multiply(unitPricePerMinuteDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        //总价
        price = price.add(minutePrice);
        return price.doubleValue();
    }
    /*

    public static void main(String[] args)
    {
        PriceRule priceRule = new PriceRule();
        priceRule.setStartMile(3);
        priceRule.setStartFare(10.0);
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);

        System.out.println(getPrice(6500, 1800, priceRule));

    }

     */


}
