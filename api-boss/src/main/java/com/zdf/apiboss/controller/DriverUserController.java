package com.zdf.apiboss.controller;

import com.zdf.apiboss.service.CarService;
import com.zdf.apiboss.service.DriverCarBindingRelationshipService;
import com.zdf.apiboss.service.DriverUserService;
import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.DriverUser;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController
{
    @Autowired
    private DriverUserService driverUserService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/driver-user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser)
    {
        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/driver-user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser)
    {
        return driverUserService.updateDriverUser(driverUser);
    }

    @PostMapping("/car")
    public ResponseResult inserCar(@RequestBody Car car)
    {
        return carService.insertCar(car);
    }

    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship)
    {
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unBind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship)
    {
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }
}
