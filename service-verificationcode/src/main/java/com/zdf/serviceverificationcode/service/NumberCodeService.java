package com.zdf.serviceverificationcode.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.NumberResponseCode;
import org.springframework.stereotype.Service;

@Service
public class NumberCodeService
{
    public ResponseResult generateNumberCode(int size)
    {
        int result = (int)((Math.random() * 9 + 1) * Math.pow(10,size-1));
        NumberResponseCode response = new NumberResponseCode();
        response.setNumber(result);
        System.out.println(result);

        return ResponseResult.success(response);
    }
}
