package com.zdf.internalcommon.utils;

public class RedisKeyUtils
{
    public static final String verificationCodePrefix = "verification-code-";

    public static final String tokenPrefix = "token-";

    public static final String blackDevicePrefix = "black-device-";

    public static String generateKeyByPhone(String phone, String identity)
    {
        return verificationCodePrefix + phone + "-" + identity;
    }

    public static String generateTokenKey(String passengerPhone, String identity, String tokenType)
    {
        return tokenPrefix + passengerPhone + "-" + identity + "-" + tokenType;
    }
}
