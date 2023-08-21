package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictController
{
    @Autowired
    private DistrictService districtService;

    @GetMapping("/dic-district")
    public ResponseResult initDictDistrict(String keywords)
    {
        return districtService.initDictDistrict(keywords);
    }
}
