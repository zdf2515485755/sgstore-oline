package com.zdf.internalcommon.constant;

import lombok.Getter;


public enum CommonStatusEnum
{
    SUCCESS(1,"success"),
    FAILL(0, "fail"),
    //验证码错误提示
    VERIFICATION_CODE_ERROR(1000, "验证码不正确"),
    //token错误提示
    TOKEN_ERROR(1100, "token错误"),
    //用户不存在错误提示
    USER_NOT_EXIST(1200, "用户不存在"),
    //计价规则不存在错误提示
    PRICE_RULE_NOT_EXIST(1300, "计价规则不存在"),
    //计价规则已存在
    PRICE_RULE_EXIST(1301, "计价规则已存在"),
    //计价规则没有发生改变
    PRICE_RULE_NO_CHANGE(1302, "计价规则没有发生改变"),
    //
    PRICE_RULE_CHANGE(1303, "计价规则发生改变"),
    //district错误提示
    DISTRICT_ERROR(1400, "district错误"),
    //司机车辆绑定错误提示
    DRIVER_CAR_BIND_NOT_EXIST(1500, "司机车辆绑定关系不存在"),
    // 司机不存在 错误提示
    DRIVER_IS_NOT_EXIST(1501, "司机不存在"),
    CITY_DRIVER_IS_NOT_EXIST(1502, "该城市没有司机"),
    DRIVER_CAR_IS_NOT_EXIST(1503,"没有合适的司机"),
    //下单失败
    ORDER_GOING_ON(1600, "有正在执行的订单"),

    DEVICE_IS_BLACK(1700, "设备在黑名单"),
    SERVICE_NOT_SERVICE(1800, "服务不提供"),
    //取消订单是吧
    ORDER_CANCEL_ERROR(1601, "订单取消失败"),

    VALIDATION_EXCEPTION(1900, "参数验证错误");
    @Getter
    private int code;
    @Getter
    private String message;

    CommonStatusEnum(int code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
