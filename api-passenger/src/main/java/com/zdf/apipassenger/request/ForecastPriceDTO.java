package com.zdf.apipassenger.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 出发地经纬度
 * 目的地经纬度
 */
@Data
public class ForecastPriceDTO
{
    @NotBlank(message = "起点经度不为空")
    @Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确格式的起点经度")
    private String depLongitude;

    @NotBlank(message = "起点纬度不为空")
    @Pattern(regexp = "^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确格式的起点纬度")
    private String depLatitude;

    @NotBlank(message = "终点经度不为空")
    @Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确格式的终点经度")
    private String destLongitude;

    @NotBlank(message = "终点纬度不为空")
    @Pattern(regexp = "^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确格式的终点纬度")
    private String destLatitude;

    @NotBlank(message = "汽车型号不能为空")
    @Pattern(regexp = "^1$", message = "请输入正确格式的车辆型号")
    private String vehicleType;

    @NotBlank(message = "城市码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "请输入正确格式的城市码")
    private String cityCode;
}
