package com.zdf.apidriver.controller;


import com.zdf.apidriver.service.VerificationCodeService;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.VerificationCodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseResult verificationCode(@RequestBody VerificationCodeRequest verificationCodeDTO)
    {
        String driverPhone = verificationCodeDTO.getDriverPhone();

        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeRequest verificationCodeDTO)
    {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        return verificationCodeService.checkCode(driverPhone, verificationCode);
    }

}
