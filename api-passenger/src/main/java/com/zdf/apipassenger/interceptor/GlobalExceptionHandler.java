package com.zdf.apipassenger.interceptor;

import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author mrzhang
 * 统一异常处理
 */
@RestControllerAdvice
@Order(99)
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e)
    {
        e.printStackTrace();
        return ResponseResult.fail(CommonStatusEnum.FAILL.getCode(), CommonStatusEnum.FAILL.getMessage());
    }
}
