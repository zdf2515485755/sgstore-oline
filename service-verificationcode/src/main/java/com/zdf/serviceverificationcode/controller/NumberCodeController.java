package com.zdf.serviceverificationcode.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.NumberResponseCode;
import com.zdf.serviceverificationcode.service.NumberCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController
{
    @Autowired
    private NumberCodeService numberCodeService;

    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberResponseCode> numberCode(@PathVariable("size") int size)
    {

        return numberCodeService.generateNumberCode(size);
    }
}
