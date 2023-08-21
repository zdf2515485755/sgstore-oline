package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.remote.MapDicDistrictClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistrictService
{
    @Autowired
    MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDictDistrict(String keywords)
    {
        //请求服务
        ResponseResult responseResult = mapDicDistrictClient.initDictDistrict(keywords);
        //解析返回结果
        //插入数据库
        return responseResult;
    }
}
