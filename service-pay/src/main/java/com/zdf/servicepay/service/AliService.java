package com.zdf.servicepay.service;

import com.zdf.internalcommon.request.OrderRequest;
import com.zdf.servicepay.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliService
{

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public void pay(Long orderId)
    {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);

        serviceOrderClient.pay(orderRequest);
    }

}
