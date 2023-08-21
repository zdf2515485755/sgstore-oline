package com.zdf.serviceorder.controller;

import com.zdf.internalcommon.dto.OrderInfo;
import com.zdf.serviceorder.mapper.OrderInfoMapper;
import com.zdf.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController
{
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderService orderService;

    @GetMapping("/testDispatch/{orderId}")
    public String testDispatch(@PathVariable("orderId") Long orderId)
    {
        System.out.println(orderId);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderService.dispatchRealTimeOrder(orderInfo);
        return "success";
    }

}
