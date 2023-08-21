package com.zdf.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.DriverCarConstant;
import com.zdf.internalcommon.dto.*;
import com.zdf.internalcommon.response.DriverCarResponse;
import com.zdf.internalcommon.response.DriverUserResponse;
import com.zdf.servicedriveruser.mapper.CarMapper;
import com.zdf.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.zdf.servicedriveruser.mapper.DriverUserMapper;
import com.zdf.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverUserService
{
    @Autowired
    private DriverUserMapper driverUserMapper;
    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;
    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;
    @Autowired
    private CarMapper carMapper;
    public ResponseResult addDriverUser(DriverUser driverUser)
    {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);

        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatus.setWorkStatus(DriverCarConstant.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);
        return ResponseResult.success("1");
    }

    public ResponseResult updateDriverUser(DriverUser driverUser)
    {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);

        return ResponseResult.success("1");
    }

    public ResponseResult getDriverUser(String driverPhone)
    {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("driver_phone", driverPhone);
        queryMap.put("state", DriverCarConstant.DRIVER_STATE_VALID);

        List<DriverUser> driverUsers = driverUserMapper.selectByMap(queryMap);
        int ifExist = DriverCarConstant.DRIVER_IS_EXIST;
        DriverUserResponse driverUserResponse = new DriverUserResponse();
        if (driverUsers.isEmpty())
        {
            ifExist = DriverCarConstant.DRIVER_IS_NOT_EXIST;
        }
        driverUserResponse.setDriverPhone(driverPhone);
        driverUserResponse.setIfExists(ifExist);
        return ResponseResult.success(driverUserResponse);
    }

    public ResponseResult isAvailableDriver(Long carId)
    {
        //根据车牌信息查询和这辆车绑定的司机
        QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
        driverCarBindingRelationshipQueryWrapper.eq("car_id", carId);
        driverCarBindingRelationshipQueryWrapper.eq("bind_state", DriverCarConstant.DRIVER_CAR_BIND);
        DriverCarBindingRelationship bindingRelationship = driverCarBindingRelationshipMapper.selectOne(driverCarBindingRelationshipQueryWrapper);
        //查询车辆信息
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("id", carId);
        Car car = carMapper.selectOne(carQueryWrapper);
        /*
        if (null == bindingRelationship)
        {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getMessage());
        }
         */
        //根据司机id查找正在工作的司机
        Long driverId = bindingRelationship.getDriverId();
        QueryWrapper<DriverUserWorkStatus> driverUserWorkStatusQueryWrapper = new QueryWrapper<>();
        driverUserWorkStatusQueryWrapper.eq("driver_id",driverId);
        driverUserWorkStatusQueryWrapper.eq("work_status", DriverCarConstant.DRIVER_WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(driverUserWorkStatusQueryWrapper);
        if (null == driverUserWorkStatus)
        {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getMessage());
        }
        //根据司机id查找司机电话号码
        DriverCarResponse driverCarResponse = new DriverCarResponse();
        QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
        driverUserQueryWrapper.eq("id", driverId);
        driverUserQueryWrapper.eq("state", DriverCarConstant.DRIVER_STATE_VALID);
        DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);
        if (null == driverUser)
        {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_CAR_IS_NOT_EXIST.getMessage());
        }
        String driverPhone = driverUser.getDriverPhone();
        driverCarResponse.setCarId(carId);
        driverCarResponse.setDriverPhone(driverPhone);
        driverCarResponse.setDriverId(driverId);
        driverCarResponse.setLicenseId(driverUser.getLicenseId());
        driverCarResponse.setVehicleNo(car.getVehicleNo());
        driverCarResponse.setVehicleType(car.getVehicleType());

        return ResponseResult.success(driverCarResponse);
    }

}
