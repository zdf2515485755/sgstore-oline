package com.zdf.internalcommon.utils;

public class SseKeyUtils
{
    public static final String sperator = "$";
    public static final String ssePrefix = "sse-";

    public static String generateSseKey(Long userId, String identity)
    {
        return ssePrefix + userId + sperator + identity;
    }
}
