package com.zdf.servicepassengeruser.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.VerificationCodeRequest;
import com.zdf.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author mrzhang
 */
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeRequest verificationCodeDTO)
    {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone)
    {
        return userService.getUserByPhone(passengerPhone);
    }
}
