package com.zdf.servicedriveruser.remote;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceMapClient
{
    @RequestMapping(method = RequestMethod.POST, value = "/terminal/add")
    ResponseResult<TerminalServiceResponse> addTerminal(@RequestParam String name, @RequestParam String desc);
    @RequestMapping(method = RequestMethod.POST, value = "/trace/add")
    ResponseResult<TraceResponse> addTrace(@RequestParam String tid);
}
