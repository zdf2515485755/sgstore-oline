package com.zdf.internalcommon.dto;

import lombok.Data;

@Data
public class TokenResult
{
    private String phone;
    private String identity;
    private String tokenType;
}
