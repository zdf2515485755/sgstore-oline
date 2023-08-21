package com.zdf.serviceprice.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.CalculatePriceRequest;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController
{
    @Autowired
    private PriceService priceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceRequest forecastPriceRequest)
    {
        return priceService.forecastPrice(forecastPriceRequest);
    }

    @PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestBody CalculatePriceRequest calculatePriceRequest)
    {
        return priceService.calculatePrice(calculatePriceRequest);
    }
}
