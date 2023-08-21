package com.zdf.servicedriveruser.service;

import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService
{
    @Autowired
    DriverUserMapper driverUserMapper;

    public ResponseResult getDriver()
    {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }
}
