package com.zdf.apiboss.service;

import com.zdf.apiboss.remote.ServiceDriverUserClient;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.DriverCarConstant;
import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverCarBindingRelationshipService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship)
    {
        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship)
    {

        return serviceDriverUserClient.unBind(driverCarBindingRelationship);
    }
}
