package com.zdf.apipassenger.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-price")
public interface ServicePriceClient
{
    @RequestMapping(method = RequestMethod.POST,value = "/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceRequest forecastPriceDTO);
}
