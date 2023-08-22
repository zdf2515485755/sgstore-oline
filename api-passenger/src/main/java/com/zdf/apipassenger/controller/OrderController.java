package com.zdf.apipassenger.controller;

import com.zdf.apipassenger.request.OrderRequest;
import com.zdf.apipassenger.service.OrderService;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author mrzhang
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody @Validated OrderRequest orderRequest)
    {
        return orderService.addOrder(orderRequest);
    }

    @PostMapping("/cancel")
    public ResponseResult cancelOrder(@RequestParam @NotNull(message = "订单id不能为空") @Positive(message = "订单号必须为正数") Long orderId)
    {
        return orderService.cancelOrder(orderId);
    }
}
