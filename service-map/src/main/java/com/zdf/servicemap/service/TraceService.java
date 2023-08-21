package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TraceResponse;
import com.zdf.servicemap.remote.TraceServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceService
{
    @Autowired
    private TraceServiceClient traceServiceClient;

    public ResponseResult<TraceResponse> addTrace(String tid)
    {
        return traceServiceClient.addTrace(tid);
    }
}
