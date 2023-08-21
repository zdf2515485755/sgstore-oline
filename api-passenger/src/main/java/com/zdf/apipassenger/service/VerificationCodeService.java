package com.zdf.apipassenger.service;

import com.zdf.apipassenger.remote.ServicePassengerUserClient;
import com.zdf.apipassenger.remote.ServiceVerificationCodeClient;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.constant.TokenConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.VerificationCodeRequest;
import com.zdf.internalcommon.response.NumberResponseCode;
import com.zdf.internalcommon.response.TokenResponse;
import com.zdf.internalcommon.utils.JwtUtils;
import com.zdf.internalcommon.utils.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService
{
    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public ResponseResult generateCode(String passengerPhone)
    {
        //获取验证码
        ResponseResult<NumberResponseCode> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumber();
        //System.out.println(numberCode);
        String key = RedisKeyUtils.generateKeyByPhone(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        //把验证码存入redis
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        return ResponseResult.success("");
    }

    public ResponseResult checkCode(String passengerPhone, String verificationCode)
    {
        System.out.println("根据电话号码获取验证码");
        String key = RedisKeyUtils.generateKeyByPhone(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("验证码" + codeRedis);
        if(StringUtils.isBlank(codeRedis) || !codeRedis.trim().equals(verificationCode.trim()))
        {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(), "");
        }
        //"检验用户"
        VerificationCodeRequest verificationCodeDTO = new VerificationCodeRequest();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.logOrRegister(verificationCodeDTO);
        //生成双token并保存到redis
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);
        String accessTokenKey = RedisKeyUtils.generateTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshTokenKey = RedisKeyUtils.generateTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }

}
