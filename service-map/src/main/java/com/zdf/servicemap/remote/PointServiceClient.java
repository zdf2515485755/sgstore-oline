package com.zdf.servicemap.remote;

import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.dto.Point;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.PointServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
public class PointServiceClient
{
    @Value("${map.key}")
    private String userKey;
    @Value("${map.sid}")
    private String sid;
    @Autowired
    RestTemplate restTemplate;
    public ResponseResult uploadPoint(PointServiceRequest pointServiceRequest)
    {
        //&points=%5B{%22location%22%3A%22116.41%2C40.02%22%2C%22locatetime%22%3A1665330773}%5D
        //[{"location":"116.41,40.02","locatetime":1665330773}]

        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.POINT_SERVICE_UPLOAD_URL);
        builder.append("?");
        builder.append("key=").append(userKey);
        builder.append("&");
        builder.append("sid=").append(sid);
        builder.append("&");
        builder.append("tid=").append(pointServiceRequest.getTid());
        builder.append("&");
        builder.append("trid=").append(pointServiceRequest.getTrid());
        builder.append("&");
        builder.append("points=");
        builder.append("%5B");

        Point[] points = pointServiceRequest.getPoints();
        for (Point point : points)
        {
            String location = point.getLocation();
            String locatetime = point.getLocatetime();

            builder.append("%7B");
            builder.append("%22location%22");
            builder.append("%3A");
            builder.append("%22").append(location).append("%22");
            builder.append("%2C");

            builder.append("%22locatetime%22");
            builder.append("%3A");
            builder.append(locatetime);
            builder.append("%7D");
        }
        builder.append("%5D");
        log.info(builder.toString());

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(URI.create(builder.toString()), null, String.class);
        return ResponseResult.success("1");
    }
}
