package com.zdf.servicedriveruser.controller;


import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdf
 * @since 2022-09-28
 */
@RestController
public class CarController
{
    @Autowired
    private CarService carService;

    @PostMapping("/car")
    public ResponseResult insertCar(@RequestBody Car car)
    {
        return carService.insertCar(car);
    }

    @GetMapping("/car")
    public ResponseResult getCar(@RequestParam Long carid)
    {
        return carService.getCar(carid);
    }
}
