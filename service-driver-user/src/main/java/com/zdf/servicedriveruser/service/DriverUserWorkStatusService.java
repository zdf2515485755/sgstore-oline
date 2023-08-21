package com.zdf.servicedriveruser.service;

import com.zdf.internalcommon.dto.DriverUserWorkStatus;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverUserWorkStatusService
{
    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult<String> updateWorkStatus(Long driverId, Integer workStatus)
    {
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("driver_id", driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        driverUserWorkStatus.setWorkStatus(workStatus);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        return ResponseResult.success("1");
    }
}
