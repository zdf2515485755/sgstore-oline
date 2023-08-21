package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.DicDistrict;
import com.zdf.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController
{
    @Autowired
    DicDistrictMapper dictDistrictMapper;

    @GetMapping("/test")
    public String testMap()
    {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("address_code", "110000");
        List<DicDistrict> dictDistrictList = dictDistrictMapper.selectByMap(queryMap);
        return dictDistrictList.get(0).toString();

    }
}
