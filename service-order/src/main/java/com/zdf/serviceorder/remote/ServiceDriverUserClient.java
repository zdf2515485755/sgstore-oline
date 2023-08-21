package com.zdf.serviceorder.remote;

import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.DriverCarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient
{
    @RequestMapping(method = RequestMethod.GET,value = "/city-driver/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam String address);

    @RequestMapping(method = RequestMethod.GET,value = "/is-available-driver")
    public ResponseResult<DriverCarResponse> isAvailableDriver(@RequestParam Long carId);

    @RequestMapping(method = RequestMethod.GET, value = "/car")
    public ResponseResult<Car> getCar(@RequestParam Long carid);
}
