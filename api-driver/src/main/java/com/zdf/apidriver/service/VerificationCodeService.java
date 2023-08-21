package com.zdf.apidriver.service;

import com.zdf.apidriver.remote.ServiceDriverUserClient;
import com.zdf.apidriver.remote.ServiceVerificationCodeClient;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.DriverCarConstant;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.constant.TokenConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.DriverUserResponse;
import com.zdf.internalcommon.response.NumberResponseCode;
import com.zdf.internalcommon.response.TokenResponse;
import com.zdf.internalcommon.utils.JwtUtils;
import com.zdf.internalcommon.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    public ResponseResult checkAndSendVerificationCode(String driverPhone)
    {
        //检查司机是否有效
        ResponseResult<DriverUserResponse> driverUserResponseResult = serviceDriverUserClient.getUser(driverPhone);
        DriverUserResponse data = driverUserResponseResult.getData();
        Integer ifExists = data.getIfExists();
        if (ifExists == DriverCarConstant.DRIVER_IS_NOT_EXIST)
        {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_IS_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_IS_NOT_EXIST.getMessage());
        }
        log.info(ifExists.toString());
        //生成验证码
        ResponseResult<NumberResponseCode> numberCodeResult = serviceVerificationCodeClient.getNumberCode(6);
        NumberResponseCode numberCode = numberCodeResult.getData();
        int number = numberCode.getNumber();
        log.info(number + "");
        //存入redis
        String driverKey = RedisKeyUtils.generateKeyByPhone(driverPhone, IdentityConstant.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(driverKey, number + "", 2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }

    public ResponseResult checkCode(String driverPhone, String verificationCode)
    {
        System.out.println("根据电话号码获取验证码");
        String key = RedisKeyUtils.generateKeyByPhone(driverPhone, IdentityConstant.DRIVER_IDENTITY);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("验证码" + codeRedis);
        if(StringUtils.isBlank(codeRedis) || !codeRedis.trim().equals(verificationCode.trim()))
        {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(), "");
        }
        //生成双token并保存到redis
        String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);
        String accessTokenKey = RedisKeyUtils.generateTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshTokenKey = RedisKeyUtils.generateTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }

}
