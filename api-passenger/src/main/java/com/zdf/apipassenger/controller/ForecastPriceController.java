package com.zdf.apipassenger.controller;

import com.zdf.apipassenger.request.ForecastPriceDTO;
import com.zdf.apipassenger.service.ForecastService;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrzhang
 */
@RestController
public class ForecastPriceController
{
    @Autowired
    private ForecastService forecastService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody @Validated ForecastPriceDTO forecastPriceDTO)
    {
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();
        return forecastService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude, cityCode, vehicleType);
    }
}
