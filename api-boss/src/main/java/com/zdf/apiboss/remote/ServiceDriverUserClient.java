package com.zdf.apiboss.remote;

import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    ResponseResult addUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.POST, value = "/car")
    ResponseResult insertCar(@RequestBody Car car);

    @RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/bind")
    ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    @RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/unbind")
    ResponseResult unBind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

}
