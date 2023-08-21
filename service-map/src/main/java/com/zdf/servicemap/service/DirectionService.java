package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ForecastPriceRequest;
import com.zdf.internalcommon.response.DirectionResponse;
import com.zdf.servicemap.remote.MapDirectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DirectionService
{
   @Autowired
   private MapDirectionClient mapDirectionClient;
    public ResponseResult driving(ForecastPriceRequest forecastPriceRequest)
    {
        //组装url
        //请求响应
        //解析结果
        String depLongitude = forecastPriceRequest.getDepLongitude();
        String depLatitude = forecastPriceRequest.getDepLatitude();
        String destLongitude = forecastPriceRequest.getDestLongitude();
        String destLatitude = forecastPriceRequest.getDestLatitude();

        DirectionResponse directionResponse = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(directionResponse);
    }
}
