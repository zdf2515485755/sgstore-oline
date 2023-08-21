package com.zdf.apipassenger.service;

import com.zdf.apipassenger.remote.ServicePriceClient;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastService
{
    @Autowired
    private ServicePriceClient servicePriceClient;
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude, String cityCode, String vehicleType)
    {
        log.info("出发地经度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地纬度：" + destLatitude);
        ForecastPriceRequest forecastPriceDTO = new ForecastPriceRequest();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setCityCode(cityCode);
        forecastPriceDTO.setVehicleType(vehicleType);
        //调用预估服务
        ResponseResult<ForecastPriceResponse> responseResult = servicePriceClient.forecastPrice(forecastPriceDTO);
        ForecastPriceResponse data = responseResult.getData();

        return ResponseResult.success(data);

    }
}
