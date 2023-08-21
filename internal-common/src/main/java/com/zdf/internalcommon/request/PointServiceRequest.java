package com.zdf.internalcommon.request;

import com.zdf.internalcommon.dto.Point;
import lombok.Data;

@Data
public class PointServiceRequest
{
    private String tid;
    private String trid;
    private Point[] points;
}
