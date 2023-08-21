package com.zdf.apipassenger.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @GetMapping("/test")
    public String Test()
    {
        return "test";
    }

    @GetMapping("/authtest")
    public ResponseResult authTest()
    {
        return ResponseResult.success("authtest");
    }

    @GetMapping("/noauthtest")
    public ResponseResult noAuthTest()
    {
        return ResponseResult.success("noauthtest");
    }

}
