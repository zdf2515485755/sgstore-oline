package com.zdf.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.DriverCarConstant;
import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.zdf.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DriverCarBindingRelationshipService
{
    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship)
    {
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstant.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);

        return ResponseResult.success("1");
    }

    public ResponseResult unBind(DriverCarBindingRelationship driverCarBindingRelationship)
    {
        LocalDateTime now = LocalDateTime.now();
        //根据车辆和司机id查询是否绑定，绑定才解绑
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("driver_id", driverCarBindingRelationship.getDriverId());
        queryMap.put("car_id", driverCarBindingRelationship.getCarId());
        queryMap.put("bind_state", DriverCarConstant.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(queryMap);
        if (driverCarBindingRelationships.isEmpty())
        {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXIST.getMessage(), "");
        }
        DriverCarBindingRelationship bindingRelationship = driverCarBindingRelationships.get(0);
        bindingRelationship.setUnBindingTime(now);
        bindingRelationship.setBindState(DriverCarConstant.DRIVER_CAR_UNBIND);

        driverCarBindingRelationshipMapper.updateById(bindingRelationship);
        return ResponseResult.success("1");
    }

    public ResponseResult getDriverCarBindingRelationship(String driverPhone)
    {
        QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
        driverUserQueryWrapper.eq("driver_phone", driverPhone);
        DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);
        Long driverUserId = driverUser.getId();

        QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
        driverCarBindingRelationshipQueryWrapper.eq("driver_id", driverUserId);
        driverCarBindingRelationshipQueryWrapper.eq("bind_state", DriverCarConstant.DRIVER_CAR_BIND);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(driverCarBindingRelationshipQueryWrapper);

        return ResponseResult.success(driverCarBindingRelationship);


    }
}
