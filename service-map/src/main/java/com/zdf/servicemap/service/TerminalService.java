package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceSearchServiceResponse;
import com.zdf.servicemap.remote.TerminalServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService
{

    @Autowired
    private TerminalServiceClient terminalServiceClient;

    public ResponseResult<TerminalServiceResponse> addTerminal(String name, String desc)
    {
        return terminalServiceClient.addTerminal(name, desc);
    }

    public ResponseResult<List<TerminalServiceResponse>> aroundSearch(String center, Integer radius)
    {
        return terminalServiceClient.aroundSearch(center, radius);
    }

    public ResponseResult<TraceSearchServiceResponse> trsearch(String tid, Long starttime, Long endtime)
    {
        return terminalServiceClient.trsearch(tid, starttime, endtime);
    }
}
