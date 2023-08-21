package com.zdf.internalcommon.request;

import lombok.Data;

/**
 * 出发地经纬度
 * 目的地经纬度
 */
@Data
public class ForecastPriceRequest
{
    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;

    private String vehicleType;

    private String cityCode;
}
