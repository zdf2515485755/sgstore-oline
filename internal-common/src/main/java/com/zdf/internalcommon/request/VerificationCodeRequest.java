package com.zdf.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeRequest
{
    private String passengerPhone;
    private String driverPhone;
    private String verificationCode;
}
