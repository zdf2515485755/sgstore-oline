package com.zdf.servicedriveruser.controller;

import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.DriverCarResponse;
import com.zdf.internalcommon.response.DriverUserResponse;
import com.zdf.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverUserController
{
    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser)
    {
        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser)
    {
        return driverUserService.updateDriverUser(driverUser);
    }

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserResponse> getUser(@PathVariable("driverPhone") String driverPhone)
    {
        return driverUserService.getDriverUser(driverPhone);
    }

    @GetMapping("/is-available-driver")
    public ResponseResult<DriverCarResponse> isAvailableDriver(@RequestParam Long carId)
    {
        return driverUserService.isAvailableDriver(carId);
    }
}
