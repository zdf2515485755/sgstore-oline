package com.zdf.apiboss.service;

import com.zdf.apiboss.remote.ServiceDriverUserClient;
import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DriverUserService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser)
    {
        return serviceDriverUserClient.addUser(driverUser);
    }

    public ResponseResult updateDriverUser(DriverUser driverUser)
    {
        return serviceDriverUserClient.updateUser(driverUser);

    }
}
