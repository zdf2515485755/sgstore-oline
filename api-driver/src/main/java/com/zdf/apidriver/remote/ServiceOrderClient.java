package com.zdf.apidriver.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-order")
public interface ServiceOrderClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/order/to-pick-up-passenger")
    ResponseResult<String> changeOrderStatus(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/arrive-departure")
    ResponseResult<String> arriveDeparture(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/pick-up-passenger")
    ResponseResult<String> pickUpPassenger(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/passenger-get-off")
    ResponseResult<String> passengerGetoff(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
    ResponseResult<String> cancel(@RequestParam Long orderId, @RequestParam String identity);

    @RequestMapping(method = RequestMethod.POST, value = "/order/startPay")
    ResponseResult<String> startPay(@RequestBody OrderRequest orderRequest);
}
