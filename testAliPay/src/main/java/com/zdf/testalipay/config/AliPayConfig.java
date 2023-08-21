package com.zdf.testalipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig
{
    private String appId;

    private String appPrivateKey;

    private String publicKey;

    private String notifyUrl;

    @PostConstruct
    public void init()
    {
        Config config = new Config();

        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.publicKey;
        config.notifyUrl = this.notifyUrl;

        Factory.setOptions(config);

    }
}
