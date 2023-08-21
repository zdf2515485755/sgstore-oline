package com.zdf.apipassenger.interceptor;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.dto.TokenResult;
import com.zdf.internalcommon.utils.JwtUtils;
import com.zdf.internalcommon.utils.RedisKeyUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class jwtInterceptor implements HandlerInterceptor
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        TokenResult tokenResult = JwtUtils.checkToken(token);

        //校验token
        if(tokenResult == null)
        {
            resultString = "accessToken invaild";
            result = false;
        }
        else
        {
            //从redis中获取token
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenType = tokenResult.getTokenType();
            String tokenKey = RedisKeyUtils.generateTokenKey(phone, identity, tokenType);
            String value = stringRedisTemplate.opsForValue().get(tokenKey);
            if(StringUtils.isBlank(value) || !value.trim().equals(token.trim()))
            {
                resultString = "accessToken invaild";
                result = false;
            }
        }

        if (!result)
        {
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
