package com.zdf.apipassenger.remote;

import com.zdf.apipassenger.request.OrderRequest;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author mrzhang
 */
@FeignClient("service-order")
public interface ServiceOrderClient
{
  //  @RequestMapping(method = RequestMethod.POST, value = "/order/add")
    @PostMapping(path = "/order/add")

    ResponseResult<String> addOrder(@RequestBody OrderRequest orderRequest);

   // @RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
    @PostMapping(path = "/order/cancel")
    ResponseResult<String> cancel(@RequestParam Long orderId, @RequestParam String identity);
}
