package com.zdf.apidriver.controller;

import com.zdf.apidriver.service.DriverService;
import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.DriverUserWorkStatus;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DriverController
{
    @Autowired
    private DriverService driverService;

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser)
    {
        return driverService.updateDriverUser(driverUser);
    }

    @PostMapping("/driver-user-work-status")
    public ResponseResult<String> updateWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus)
    {
        return driverService.updateWorkStatus(driverUserWorkStatus);
    }

    @GetMapping("/driver-car-binding-relationship/get")
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(HttpServletRequest httpServletRequest)
    {
        return driverService.getDriverCarBindingRelationship(httpServletRequest);
    }

}
