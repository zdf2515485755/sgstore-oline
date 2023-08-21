package com.zdf.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdf
 * @since 2022-10-16
 */
@Data
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long passengerId;

    private String passengerPhone;

    private Long driverId;

    private String driverPhone;

    private Long carId;

    /**
     * 行政地区编码
     */
    private String address;

    private LocalDateTime orderTime;

    private LocalDateTime departTime;

    private String departure;

    private String depLongitude;

    private String depLatitude;

    private String destination;

    private String desLongitude;

    private String desLatitude;

    private Integer encrypt;

    private String fareType;

    private Integer fareVersion;

    private String receiveOrderCarLongitude;

    private String receiveOrderCarLatitude;

    private LocalDateTime receiveOrderTime;

    private String licenseId;

    private String vehicleNo;

    private String vehicleType;

    private LocalDateTime toPickUpPassengerTime;

    private String toPickUpPassengerLongitude;

    private String toPickUpPassengerLatitude;

    private String toPickUpPassengerAddress;

    private LocalDateTime driverArrivedDepartureTime;

    private LocalDateTime pickUpPassengerTime;

    private String pickUpPassengerLongitude;

    private String pickUpPassengerLatitude;

    private LocalDateTime passengerGetoffTime;

    private String passengerGetoffLongitude;

    private String passengerGetoffLatitude;

    private LocalDateTime cancelTime;

    private Integer cancelOperator;

    private Integer cancelTypeCode;

    private Long driveMile;

    private Long driveTime;

    private Integer orderStatus;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private Double price;

}
