package com.zdf.servicemap.remote;

import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TraceResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TraceServiceClient
{
    @Value("${map.key}")
    private String userKey;
    @Value("${map.sid}")
    private String sid;
    @Autowired
    RestTemplate restTemplate;

    public ResponseResult addTrace(String tid)
    {
        //https://tsapi.amap.com/v1/track/trace/add?key=4662262223a037bc3c06c5169f2f14af&sid=808074&tid=584693620
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.TRACE_SERVICE_ADD_URL);
        builder.append("?");
        builder.append("key=").append(userKey);
        builder.append("&");
        builder.append("sid=").append(sid);
        builder.append("&");
        builder.append("tid=").append(tid);
        log.info(builder.toString());

        ResponseEntity<String> resultEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
        String body = resultEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String trname = "";
        if(data.has("trname"))
        {
            trname = data.getString("trname");
        }
        String trid = data.getString("trid");
        TraceResponse traceResponse = new TraceResponse();
        traceResponse.setTrid(trid);
        traceResponse.setTrname(trname);

        return ResponseResult.success(traceResponse);

    }
}
