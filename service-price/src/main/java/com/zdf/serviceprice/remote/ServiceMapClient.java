package com.zdf.serviceprice.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
    ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceRequest forecastPriceRequest);
}
