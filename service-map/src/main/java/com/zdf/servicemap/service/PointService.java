package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.PointServiceRequest;
import com.zdf.servicemap.remote.PointServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService
{
    @Autowired
    private PointServiceClient pointServiceClient;

    public ResponseResult uploadPoint(PointServiceRequest pointServiceRequest)
    {
        return pointServiceClient.uploadPoint(pointServiceRequest);
    }
}
