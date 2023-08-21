package com.zdf.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdf
 * @since 2022-09-28
 */
@Data
public class Car implements Serializable
{

    private static final long serialVersionUID = 1L;


    private Long id;

    private String address;

    /**
     * 车牌号
     */
    private String vehicleNo;

    private String plateColor;

    private Integer seats;

    private String brand;

    private String model;

    private String vehicleType;

    private String ownerName;

    private String vehicleColor;

    private String engineId;

    /**
     * 车辆vin码
     */
    private String vin;

    private LocalDate certifyDateA;

    private String fuelType;

    private String engineDisplace;

    private String transAgency;

    private String transArea;

    private LocalDate transDateStart;

    private LocalDate transDateEnd;

    private LocalDate certifyDateB;

    private String fixState;

    private LocalDate nextFixDate;

    private String checkState;

    private String feePrintId;

    private String gpsBrand;

    private String gpsModel;

    private LocalDate gpsInstallDate;

    private LocalDate registerDate;

    private Integer commercialType;

    private String fareType;

    private Integer state;

    private String tid;

    private String trid;

    private String trname;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
