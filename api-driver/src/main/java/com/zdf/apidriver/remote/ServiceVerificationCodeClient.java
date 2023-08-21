package com.zdf.apidriver.remote;


import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.NumberResponseCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-verificationcode")
public interface ServiceVerificationCodeClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/numberCode/{size}")
    ResponseResult<NumberResponseCode> getNumberCode(@PathVariable("size") int size);
}
