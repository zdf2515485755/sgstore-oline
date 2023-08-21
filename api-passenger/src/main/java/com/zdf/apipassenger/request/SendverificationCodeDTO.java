package com.zdf.apipassenger.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SendverificationCodeDTO
{
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "电话号码格式要正确")
    private String passengerPhone;

    private String verificationCode;
}
