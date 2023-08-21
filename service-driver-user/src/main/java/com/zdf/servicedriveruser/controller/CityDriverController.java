package com.zdf.servicedriveruser.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.service.CityDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city-driver")
public class CityDriverController
{
    @Autowired
    private CityDriverService cityDriverService;

    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam String address)
    {
        return cityDriverService.isAvailableDriver(address);
    }

}
