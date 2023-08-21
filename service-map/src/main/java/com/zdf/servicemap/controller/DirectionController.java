package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.DirectionResponse;
import com.zdf.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController
{
    @Autowired
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceRequest forecastPriceRequest)
    {
        return directionService.driving(forecastPriceRequest);
    }
}
