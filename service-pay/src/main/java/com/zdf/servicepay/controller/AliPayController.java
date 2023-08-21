package com.zdf.servicepay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.zdf.servicepay.service.AliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mrzhang
 */
@RequestMapping("/alipay")
@Controller
@ResponseBody
public class AliPayController
{
    @Autowired
    private AliService aliService;

    @GetMapping("/pay")
    public String pay(String subject, String outTradeNo, String totalAmount)
    {
        AlipayTradePagePayResponse response;

        try {
            response = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, "");
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return response.getBody();
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest httpServletRequest) throws Exception {
        String tradeStatus = httpServletRequest.getParameter("trade_status");
        Map<String, String> params = new HashMap<>();
        
        if ("TRADE_SUCCESS".equals(tradeStatus.trim()))
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
            String tradeNo = params.get("out_trade_no");
            long orderId = Long.parseLong(tradeNo);
            aliService.pay(orderId);

        }
        else
        {
            System.out.println("验证不通过");
        }

        return "success";
    }
}
