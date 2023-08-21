package com.zdf.apidriver.service;

import com.zdf.apidriver.remote.ServiceDriverUserClient;
import com.zdf.internalcommon.dto.*;
import com.zdf.internalcommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class DriverService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateDriverUser(DriverUser driverUser)
    {
        return serviceDriverUserClient.updateUser(driverUser);
    }


    public ResponseResult<String> updateWorkStatus(DriverUserWorkStatus driverUserWorkStatus)
    {

        return serviceDriverUserClient.updateWorkStatus(driverUserWorkStatus);
    }

    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(HttpServletRequest httpServletRequest)
    {
        String authorization = httpServletRequest.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(authorization);
        String phone = tokenResult.getPhone();
        return serviceDriverUserClient.getDriverCarBindingRelationship(phone);
    }

}
