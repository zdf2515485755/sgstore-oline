package com.zdf.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zdf.internalcommon.constant.TokenConstant;
import com.zdf.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils
{
    //盐
    private static final String SIGN = "zasg1524&'%$";

    private static final String JWT_PHONE = "phone";

    private static final String JWT_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    public static String generatorToken(String phone, String identity, String tokenType)
    {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_PHONE, phone);
        map.put(JWT_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        //防止每次生成一样的token
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();

        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );

        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static TokenResult parseToken(String token)
    {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_PHONE).asString();
        String identity = verify.getClaim(JWT_IDENTITY).asString();
        String tokenType = verify.getClaim(JWT_TOKEN_TYPE).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        tokenResult.setTokenType(tokenType);

        return tokenResult;
    }

    /**
     * 检验解析token是否出错
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token)
    {
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);

        }catch (Exception e){

        }
        return  tokenResult;
    }

    public static void main(String[] args) {
        String s = JwtUtils.generatorToken("13458956241", "1", TokenConstant.ACCESS_TOKEN_TYPE);
        System.out.println(s);
        System.out.println(JwtUtils.parseToken(s));
    }

}
