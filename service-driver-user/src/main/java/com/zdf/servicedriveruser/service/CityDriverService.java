package com.zdf.servicedriveruser.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDriverService
{
    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult isAvailableDriver(String address)
    {
        int count = driverUserMapper.selectDriverUserCountByAddress(address);
        return count > 0 ? ResponseResult.success(true):ResponseResult.success(false);
    }

}
