package com.zdf.internalcommon.response;

import lombok.Data;

@Data
public class DriverCarResponse
{
    private Long driverId;
    private String driverPhone;
    private Long carId;
    private String licenseId;
    private String vehicleNo;
    private String vehicleType;
}
