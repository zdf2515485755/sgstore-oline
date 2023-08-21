package com.zdf.servicemap.controller;

import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/terminal")
public class TerminalController
{
    @Autowired
    private TerminalService terminalService;

    @PostMapping("/add")
    public ResponseResult addTerminal(@RequestParam String name, @RequestParam String desc)
    {
        return terminalService.addTerminal(name, desc);
    }

    @PostMapping("/aroundsearch")
    public ResponseResult aroundSearch(@RequestParam String center, @RequestParam Integer radius)
    {
        return terminalService.aroundSearch(center, radius);
    }

    @PostMapping("/trsearch")
    public ResponseResult trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime)
    {
        return terminalService.trsearch(tid, starttime, endtime);
    }
}
