package com.zdf.servicedriveruser.controller;


import com.zdf.internalcommon.dto.DriverUserWorkStatus;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdf
 * @since 2022-09-29
 */
@RestController

public class DriverUserWorkStatusController
{
    @Autowired
    private DriverUserWorkStatusService driverUserWorkStatusService;

    @PostMapping("/driver-user-work-status")
    public ResponseResult updateWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus)
    {
        Long driverId = driverUserWorkStatus.getDriverId();
        Integer workStatus = driverUserWorkStatus.getWorkStatus();
        return driverUserWorkStatusService.updateWorkStatus(driverId, workStatus);
    }
}
