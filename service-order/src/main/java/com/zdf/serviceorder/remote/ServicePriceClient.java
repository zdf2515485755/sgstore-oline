package com.zdf.serviceorder.remote;

import com.zdf.internalcommon.dto.PriceRule;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.CalculatePriceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/price-rule/is-new")
    ResponseResult<Boolean> isNewPriceRule(@RequestParam String fareType, @RequestParam Integer fareVersion);

    @RequestMapping(method = RequestMethod.POST, value = "/price-rule/is-exist")
    ResponseResult<Boolean> isExist(@RequestBody PriceRule priceRule);

    @RequestMapping(method = RequestMethod.POST, value = "/calculate-price")
    ResponseResult<Double> calculatePrice(@RequestBody CalculatePriceRequest calculatePriceRequest);
}
