package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.remote.MapServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService
{
    @Autowired
    private MapServiceClient mapServiceClient;

    public ResponseResult addService(String name)
    {
        return mapServiceClient.addService(name);
    }
}
