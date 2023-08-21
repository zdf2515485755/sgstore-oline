package com.zdf.servicemap.remote;

import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j

public class MapDirectionClient
{
    @Value("${map.key}")
    private String userKey;
    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude)
    {
        //组装url
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.DIRECTION_URL);
        builder.append("?");
        builder.append("origin=" + depLongitude + "," + depLatitude);
        builder.append("&");
        builder.append("destination=" + destLongitude + "," + destLatitude);
        builder.append("&");
        builder.append("extensions=base");
        builder.append("&");
        builder.append("output=json");
        builder.append("&");
        builder.append("key=" + userKey);
        log.info(builder.toString());
        //调用地图api请求响应
        ResponseEntity<String> directionForEntity = restTemplate.getForEntity(builder.toString(), String.class);
        String directionEntityString = directionForEntity.getBody();
        //log.info(directionForEntity.getBody());
        //解析响应结果
        DirectionResponse directionResponse = parseDirectionString(directionEntityString);
        return directionResponse;
    }

    public DirectionResponse parseDirectionString(String directionEntityString)
    {
        DirectionResponse directionResponse = null;
        try
        {
            JSONObject directionObject = JSONObject.fromObject(directionEntityString);
            if (directionObject.has(MapConfigConstant.STATUS))
            {
                int status = directionObject.getInt(MapConfigConstant.STATUS);
                if (status == 1)
                {
                    if (directionObject.has(MapConfigConstant.ROUTE))
                    {
                        JSONObject route = directionObject.getJSONObject(MapConfigConstant.ROUTE);
                        JSONArray paths = route.getJSONArray(MapConfigConstant.PATHS);
                        JSONObject path = paths.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if(path.has(MapConfigConstant.DISTANCE))
                        {
                            int distance = path.getInt(MapConfigConstant.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if(path.has(MapConfigConstant.DURATION))
                        {
                            int duration = path.getInt(MapConfigConstant.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }

            }
        }catch (Exception e){

        }
        return directionResponse;
    }
}
