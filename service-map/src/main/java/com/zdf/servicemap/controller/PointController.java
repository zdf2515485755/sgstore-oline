package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.PointServiceRequest;
import com.zdf.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController
{
    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult uploadPoint(@RequestBody PointServiceRequest pointServiceRequest)
    {
        return pointService.uploadPoint(pointServiceRequest);
    }
}
