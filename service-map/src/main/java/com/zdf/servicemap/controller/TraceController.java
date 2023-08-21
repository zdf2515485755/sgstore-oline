package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trace")
public class TraceController
{
    @Autowired
    private TraceService traceService;

    @PostMapping("/add")
    public ResponseResult addTrace(String tid)
    {
        return traceService.addTrace(tid);
    }
}
