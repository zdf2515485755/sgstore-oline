package com.zdf.apipassenger.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zdf.apipassenger.constraints.DateTimeRangeCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @author mrzhang
 */
@Data
public class OrderRequest
{
    private Long orderId;
    //下单行政区域
    @NotBlank(message = "城市码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "请输入正确格式的城市码")
    private String address;
    //出发时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @DateTimeRangeCheck
    private LocalDateTime departTime;
    //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @DateTimeRangeCheck
    private LocalDateTime orderTime;
    //出发地址
    private String departure;
    //出发经度
    @NotBlank(message = "起点经度不为空")
    @Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确格式的起点经度")
    private String depLongitude;
    //出发纬度
    @NotBlank(message = "起点纬度不为空")
    @Pattern(regexp = "^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确格式的起点纬度")
    private String depLatitude;
    //目的地
    private String destination;
    //目的地经度
    @NotBlank(message = "终点经度不为空")
    @Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确格式的终点经度")
    private String destLongitude;
    //目的地纬度
    @NotBlank(message = "终点纬度不为空")
    @Pattern(regexp = "^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确格式的终点纬度")
    private String destLatitude;
    //坐标加密标识
    private Integer encrypt;
    //运价类型编码
    private String fareType;

    private Integer fareVersion;

    @NotBlank(message = "汽车型号不能为空")
    @Pattern(regexp = "^1$", message = "请输入正确格式的车辆型号")
    private String vehicleType;

    private Long passengerId;

    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "电话号码格式要正确")
    private String passengerPhone;

    private String deviceCode;

    private LocalDateTime toPickUpPassengerTime;

    private String toPickUpPassengerLongitude;

    private String toPickUpPassengerLatitude;

    private String toPickUpPassengerAddress;

    private String pickUpPassengerLongitude;

    private String pickUpPassengerLatitude;

    private String passengerGetoffLongitude;

    private String passengerGetoffLatitude;

}
