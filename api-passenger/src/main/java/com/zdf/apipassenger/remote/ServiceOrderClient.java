package com.zdf.apipassenger.remote;

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
    @RequestMapping(method = RequestMethod.POST, value = "/order/add")
    ResponseResult<String> addOrder(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
    ResponseResult<String> cancel(@RequestParam Long orderId, @RequestParam String identity);
}
