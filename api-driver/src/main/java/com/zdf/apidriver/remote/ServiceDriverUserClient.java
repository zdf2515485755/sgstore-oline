package com.zdf.apidriver.remote;

import com.zdf.internalcommon.dto.*;
import com.zdf.internalcommon.response.DriverUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient
{
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.GET, value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverUserResponse> getUser(@PathVariable("driverPhone") String driverPhone);

    @RequestMapping(method = RequestMethod.GET, value = "/car")
    public ResponseResult<Car> getCar(@RequestParam Long carid);

    @RequestMapping(method = RequestMethod.POST, value = "/driver-user-work-status")
    public ResponseResult<String> updateWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus);

    @RequestMapping(method = RequestMethod.GET, value = "/driver-car-binding-relationship/get")
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone);
}
