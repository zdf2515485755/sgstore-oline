package com.zdf.servicemap.service;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.remote.TerminalServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService
{

    @Autowired
    private TerminalServiceClient terminalServiceClient;

    public ResponseResult addTerminal(String name, String desc)
    {
        return terminalServiceClient.addTerminal(name, desc);
    }

    public ResponseResult aroundSearch(String center, Integer radius)
    {
        return terminalServiceClient.aroundSearch(center, radius);
    }

    public ResponseResult trsearch(String tid, Long starttime, Long endtime)
    {
        return terminalServiceClient.trsearch(tid, starttime, endtime);
    }
}
