package com.zdf.apipassenger.remote;


import com.zdf.internalcommon.dto.PassengerUser;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.VerificationCodeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseResult logOrRegister(VerificationCodeRequest verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/user/{phone}")
    public ResponseResult<PassengerUser> getUser(@PathVariable("phone") String passengerPhone);
}

