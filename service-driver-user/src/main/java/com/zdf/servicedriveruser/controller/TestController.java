package com.zdf.servicedriveruser.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @Autowired
    private TestService driverUserService;

    @GetMapping("/getDriver")
    public ResponseResult getDriver()
    {
        return driverUserService.getDriver();
    }
}
