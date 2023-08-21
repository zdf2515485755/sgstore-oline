package com.zdf.internalcommon.request;

import com.zdf.internalcommon.dto.Point;
import lombok.Data;

@Data
public class ApiDriverPointRequest
{
    private Long carid;
    private Point[] points;
}
