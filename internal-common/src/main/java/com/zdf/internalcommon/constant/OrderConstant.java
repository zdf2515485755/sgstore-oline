package com.zdf.internalcommon.constant;

/**
 * 订单状态
 */
public class OrderConstant
{
    //无效订单(叫车不成功)
    public static final int ORDER_INVAILD = 0;
    //订单开始
    public static final int ORDER_START = 1;
    //司机接单
    public static final int DRIVER_RECEIVE_ORDER = 2;
    //司机去接乘客
    public static final int DRIVER_TO_PICK_UP_PASSENGER = 3;
    //司机到达目的地
    public static final int DRIVER_ARRIVED_DEPARTURE = 4;
    //乘客上车
    public static final int PICK_UP_PASSENGER = 5;
    //乘客下车
    public static final int PASSENGER_GET_OFF = 6;
    //开始支付
    public static final int TO_START_PAY = 7;
    //支付成功
    public static final int SUCCESS_PAY = 8;
    //订单取消
    public static final int ORDER_CANCEL = 9;
    //乘客正常取消
    public static final int CANCEL_PASSENGER_BEFORE = 1;
    //司机正常取消
    public static final int CANCEL_DRIVER_BEFORE = 2;
    //平台正常取消
    public static final int CANCEL_PLATFORM_BEFORE = 3;
    //乘客违约取消
    public static final int CANCEL_PASSENGER_ILLEGAL = 4;
    //司机违约取消
    public static final int CANCEL_DRIVER_ILLEGAL = 5;
}
