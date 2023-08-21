package com.zdf.servicessepush.controller;

import com.zdf.internalcommon.utils.SseKeyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SseController
{
   public static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity)
    {
        System.out.println(userId);
        SseEmitter sseEmitter = new SseEmitter(0L);
        String sseKey = SseKeyUtils.generateSseKey(userId, identity);

        sseEmitterMap.put(sseKey, sseEmitter);
        return sseEmitter;
    }

    /**
     * 推送消息
     * @param
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String pushContent(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content)
    {
        String sseKey = SseKeyUtils.generateSseKey(userId, identity);
        try {
            if (sseEmitterMap.containsKey(sseKey))
            {
                sseEmitterMap.get(sseKey).send(content);
            }
            else
            {
                return "此用户不存在";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userId + "发送了" + content;
    }

    @GetMapping("/close")
    public String closeConnection(@RequestParam Long userId, @RequestParam String identity)
    {
        String sseKey = SseKeyUtils.generateSseKey(userId, identity);
        if (sseEmitterMap.containsKey(sseKey))
        {
            sseEmitterMap.remove(sseKey);
        }
        else
        {
            return "此用户不存在";
        }
        return "关闭成功";
    }

}
