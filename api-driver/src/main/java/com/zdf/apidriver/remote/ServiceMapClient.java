package com.zdf.apidriver.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.PointServiceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    ResponseResult<String> uploadPoint(@RequestBody PointServiceRequest pointServiceRequest);
}
