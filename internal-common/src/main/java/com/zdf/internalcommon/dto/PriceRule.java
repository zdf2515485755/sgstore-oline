package com.zdf.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdf
 * @since 2022-10-18
 */
@Data
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 计程单价
     */
    private Double unitPricePerMile;

    /**
     * 每分钟多少钱
     */
    private Double unitPricePerMinute;

    private String fareType;

    private Integer fareVersion;
}
