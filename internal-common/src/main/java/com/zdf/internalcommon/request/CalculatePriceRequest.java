package com.zdf.internalcommon.request;

import lombok.Data;

@Data
public class CalculatePriceRequest
{
    private String vehicleType;

    private String cityCode;

    private Long driveMile;

    private Long driveTime;
}
