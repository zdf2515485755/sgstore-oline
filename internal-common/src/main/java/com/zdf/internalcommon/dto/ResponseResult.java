package com.zdf.internalcommon.dto;

import com.zdf.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult <T>
{
    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private T data;

    /**
     * 返回正确响应
     * @param data
     * @return
     * @param <T>
     */

    public static <T> ResponseResult success(T data)
    {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage()).setData(data);
    }

    /**
     * 返回自定义失败错误码，提示信息，具体错误
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data)
    {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /**
     * 返回自定义错误码，提示信息
     * @param code
     * @param message
     * @return
     */

    public static ResponseResult fail(int code, String message)
    {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 返回自定义错误信息
     * @param data
     * @return
     * @param <T>
     */
    public static<T> ResponseResult fail(T data)
    {
        return new ResponseResult().setCode(CommonStatusEnum.FAILL.getCode()).setMessage(CommonStatusEnum.FAILL.getMessage()).setData(data);
    }

}
