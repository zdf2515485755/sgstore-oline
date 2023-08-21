package com.zdf.internalcommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest
{
    private Long orderId;
    //下单行政区域
    private String address;
    //出发时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;
    //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    //出发地址
    private String departure;
    //出发经度
    private String depLongitude;
    //出发纬度
    private String depLatitude;
    //目的地
    private String destination;
    //目的地经度
    private String desLongitude;
    //目的地纬度
    private String desLatitude;
    //坐标加密标识
    private Integer encrypt;
    //运价类型编码
    private String fareType;

    private Integer fareVersion;

    private String vehicleType;

    private Long passengerId;

    private String passengerPhone;

    private String deviceCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toPickUpPassengerTime;

    private String toPickUpPassengerLongitude;

    private String toPickUpPassengerLatitude;

    private String toPickUpPassengerAddress;

    private String pickUpPassengerLongitude;

    private String pickUpPassengerLatitude;

    private String passengerGetoffLongitude;

    private String passengerGetoffLatitude;

}
