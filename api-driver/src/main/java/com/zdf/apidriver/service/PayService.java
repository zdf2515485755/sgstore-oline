package com.zdf.apidriver.service;

import com.zdf.apidriver.remote.ServiceOrderClient;
import com.zdf.apidriver.remote.ServiceSsePushClient;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.OrderRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService
{
    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    @Autowired
    private ServiceOrderClient serviceOrderClient;
    public ResponseResult pushPayInfo(Long orderId, Long passengerId, String price)
    {
        JSONObject result = new JSONObject();
        result.put("orderId", orderId);
        result.put("price", price);

        String s = serviceSsePushClient.pushContent(passengerId, IdentityConstant.PASSENGER_IDENTITY, result.toString());
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);
        serviceOrderClient.startPay(orderRequest);
        return ResponseResult.success(s);

    }
}
