package com.zdf.apipassenger.service;

import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.constant.TokenConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.dto.TokenResult;
import com.zdf.internalcommon.response.TokenResponse;
import com.zdf.internalcommon.utils.JwtUtils;
import com.zdf.internalcommon.utils.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public ResponseResult refreshToken(String refreshTokenSrc)
    {
        //解析原token
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null)
        {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        //根据原token去redis中查找
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        String tokenType = tokenResult.getTokenType();
        String refreshTokenKey = RedisKeyUtils.generateTokenKey(phone, identity, tokenType);
        String value = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        if(StringUtils.isBlank(value) || !value.trim().equals(refreshTokenSrc.trim()))
        {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        //生成新的双token
        String accessTokenKey = RedisKeyUtils.generateTokenKey(phone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String accessToken = JwtUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
