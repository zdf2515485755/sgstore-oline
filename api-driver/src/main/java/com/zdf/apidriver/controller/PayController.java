package com.zdf.apidriver.controller;

import com.zdf.apidriver.service.PayService;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController
{
    @Autowired
    private PayService payService;

    @PostMapping("/push-payInfo")
    public ResponseResult pushPayInfo(@RequestParam Long orderId, @RequestParam Long passengerId, @RequestParam String price)
    {
        return payService.pushPayInfo(orderId, passengerId, price);
    }
}
