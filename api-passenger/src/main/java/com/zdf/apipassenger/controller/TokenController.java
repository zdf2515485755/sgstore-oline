package com.zdf.apipassenger.controller;

import com.zdf.apipassenger.service.TokenService;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenRreshService;

    /**
     * accesstoken过期刷新接口
     * @param tokenResponse
     * @return
     */
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse)
    {
        String refreshTokenSrc = tokenResponse.getRefreshToken();
        return tokenRreshService.refreshToken(refreshTokenSrc);
    }
}
