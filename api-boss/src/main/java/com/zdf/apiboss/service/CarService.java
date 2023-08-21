package com.zdf.apiboss.service;

import com.zdf.apiboss.remote.ServiceDriverUserClient;
import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult insertCar(Car car)
    {
        return serviceDriverUserClient.insertCar(car);
    }
}
