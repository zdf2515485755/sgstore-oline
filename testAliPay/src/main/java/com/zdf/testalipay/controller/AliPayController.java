package com.zdf.testalipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/alipay")
@Controller
@ResponseBody
public class AliPayController
{
    @GetMapping("/pay")
    public String pay(String subject, String outTradeNo, String totalAmount)
    {
        AlipayTradePagePayResponse response = null;

        try {
            response = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, "");
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return response.getBody();
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest httpServletRequest) throws Exception {
        String trade_status = httpServletRequest.getParameter("trade_status");
        Map<String, String> params = new HashMap<>();
        
        if (trade_status.trim().equals("TRADE_SUCCESS"))
        {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            for (String name: parameterMap.keySet())
            {
                params.put(name, httpServletRequest.getParameter(name));
            }
        }

        if (Factory.Payment.Common().verifyNotify(params))
        {
            System.out.println("验证通过");
        }
        else
        {
            System.out.println("验证不通过");
        }

        return "success";
    }
}
