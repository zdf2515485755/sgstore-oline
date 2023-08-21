package com.zdf.apidriver.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/push")
    String pushContent(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content);
}
