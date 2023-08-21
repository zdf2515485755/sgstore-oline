package com.zdf.apidriver.controller;

import com.zdf.apidriver.service.DriverOrderService;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class DriverOrderController
{
    @Autowired
    private DriverOrderService driverOrderService;

    @PostMapping( "/to-pick-up-passenger")
    public ResponseResult<String> changeOrderStatus(@RequestBody OrderRequest orderRequest)
    {
        return driverOrderService.changeOrderStatus(orderRequest);
    }

    @PostMapping( "/arrive-departure")
    public ResponseResult<String> arriveDeparture(@RequestBody OrderRequest orderRequest)
    {
       return driverOrderService.arriveDeparture(orderRequest);
    }

    @PostMapping( "/pick-up-passenger")
    public ResponseResult<String> pickUpPassenger(@RequestBody OrderRequest orderRequest)
    {
        return driverOrderService.pickUpPassenger(orderRequest);
    }

    @PostMapping( "/passenger-get-off")
    public ResponseResult<String> passengerGetoff(@RequestBody OrderRequest orderRequest)
    {
        return driverOrderService.passengerGetoff(orderRequest);
    }

    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId)
    {
        return driverOrderService.cancel(orderId);
    }
}
