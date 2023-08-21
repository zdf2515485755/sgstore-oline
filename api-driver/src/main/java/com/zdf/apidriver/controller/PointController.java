package com.zdf.apidriver.controller;

import com.zdf.apidriver.service.PointService;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ApiDriverPointRequest;
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
    public ResponseResult uploadPoint(@RequestBody ApiDriverPointRequest apiDriverPointRequest)
    {
        return pointService.uploadPoint(apiDriverPointRequest);
    }
}
