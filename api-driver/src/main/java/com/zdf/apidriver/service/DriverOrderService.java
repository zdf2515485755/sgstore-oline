package com.zdf.apidriver.service;

import com.zdf.apidriver.remote.ServiceOrderClient;
import com.zdf.internalcommon.constant.IdentityConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverOrderService
{
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult<String> changeOrderStatus(OrderRequest orderRequest)
    {
        return serviceOrderClient.changeOrderStatus(orderRequest);
    }

    public ResponseResult<String> arriveDeparture(OrderRequest orderRequest)
    {
        return serviceOrderClient.arriveDeparture(orderRequest);
    }

    public ResponseResult<String> pickUpPassenger(OrderRequest orderRequest)
    {
        return serviceOrderClient.pickUpPassenger(orderRequest);
    }

    public ResponseResult<String> passengerGetoff(OrderRequest orderRequest)
    {
        return serviceOrderClient.passengerGetoff(orderRequest);
    }

    public ResponseResult cancel(Long orderId)
    {
        return serviceOrderClient.cancel(orderId, IdentityConstant.DRIVER_IDENTITY);
    }
}
