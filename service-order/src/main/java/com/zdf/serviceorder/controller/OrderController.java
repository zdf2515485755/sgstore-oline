package com.zdf.serviceorder.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.OrderRequest;
import com.zdf.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    /**
     * 下订单
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest)
    {
        return orderService.addOrder(orderRequest);
    }

    /**
     * 去接乘客
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult changeOrderStatus(@RequestBody OrderRequest orderRequest)
    {
        return orderService.changeOrderStatus(orderRequest);
    }

    /**
     * 到达乘客所在地
     */
    @PostMapping("/arrive-departure")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest)
    {
        return orderService.arriveDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest)
    {
        return orderService.pickUpPassenger(orderRequest);
    }

    /**
     * 司机到达目的地
     */
    @PostMapping("/passenger-get-off")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest)
    {
        return orderService.passengerGetoff(orderRequest);
    }

    @PostMapping("/startPay")
    public ResponseResult startPay(@RequestBody OrderRequest orderRequest)
    {
        return orderService.startPay(orderRequest);
    }

    /**
     * 支付
     * @param orderRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult<String> pay(@RequestBody OrderRequest orderRequest)
    {
        return orderService.pay(orderRequest);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param identity
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity)
    {
        return orderService.cancel(orderId, identity);
    }
}
