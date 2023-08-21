package com.zdf.apipassenger.service;

import com.zdf.apipassenger.remote.ServicePassengerUserClient;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.dto.TokenResult;
import com.zdf.internalcommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService
{
    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    public ResponseResult getUserByAccessToken(String accessToken)
    {
        log.info("token: " + accessToken);
        //解析token得到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone: " + phone);
        //根据手机号查询用户返回

        return servicePassengerUserClient.getUser(phone);

    }
}
