package com.zdf.serviceorder.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceSearchServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/terminal/aroundsearch")
    ResponseResult<List<TerminalServiceResponse>> aroundSearch(@RequestParam String center, @RequestParam Integer radius);

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/trsearch")
    ResponseResult<TraceSearchServiceResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);

}
