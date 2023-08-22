package com.zdf.apipassenger.service;

import com.zdf.apipassenger.remote.ServiceOrderClient;
import com.zdf.apipassenger.request.OrderRequest;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mrzhang
 */
@Service
@Slf4j
public class OrderService
{
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult addOrder(OrderRequest orderRequest)
    {
        return serviceOrderClient.addOrder(orderRequest);
    }

    public ResponseResult cancelOrder(Long orderId)
    {
        return serviceOrderClient.cancel(orderId, IdentityConstant.PASSENGER_IDENTITY);
    }
}
