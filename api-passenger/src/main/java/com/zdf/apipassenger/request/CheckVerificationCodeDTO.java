package com.zdf.apipassenger.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CheckVerificationCodeDTO
{
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "电话号码格式要正确")
    private String passengerPhone;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "请输入正确格式的验证码")
    private String verificationCode;
}
