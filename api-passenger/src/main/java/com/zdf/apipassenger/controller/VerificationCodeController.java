package com.zdf.apipassenger.controller;


import com.zdf.apipassenger.request.CheckVerificationCodeDTO;
import com.zdf.apipassenger.request.SendverificationCodeDTO;
import com.zdf.apipassenger.service.VerificationCodeService;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController
{
    @Autowired
    private VerificationCodeService verificationCodeService;

    //获取验证码
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody @Validated SendverificationCodeDTO sendverificationCodeDTO)
    {
        String passengerPhone = sendverificationCodeDTO.getPassengerPhone();
        //System.out.println("phonenumber:" + passengerPhone);

        return verificationCodeService.generateCode(passengerPhone);
    }

    //校验验证码
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody @Validated CheckVerificationCodeDTO checkVerificationCodeDTO)
    {
        String passengerPhone = checkVerificationCodeDTO.getPassengerPhone();
        String verificationCode = checkVerificationCodeDTO.getVerificationCode();
        return verificationCodeService.checkCode(passengerPhone, verificationCode);
    }
}
