package com.zdf.servicemap.remote;

import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceSearchServiceResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TerminalServiceClient
{
    @Value("${map.key}")
    private String userKey;
    @Value("${map.sid}")
    private String sid;
    @Autowired
    RestTemplate restTemplate;

    public ResponseResult<TerminalServiceResponse> addTerminal(String name, String desc)
    {
        //?key=4662262223a037bc3c06c5169f2f14af&sid=808074&name=taxi1
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.TERMINAL_SERVICE_ADD_URL);
        builder.append("?");
        builder.append("key=" + userKey);
        builder.append("&");
        builder.append("sid=" + sid);
        builder.append("&");
        builder.append("name=" + name);
        builder.append("&");
        builder.append("desc=" + desc);

        log.info(builder.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalServiceResponse terminalServiceResponse = new TerminalServiceResponse();
        terminalServiceResponse.setTid(tid);

        return ResponseResult.success(terminalServiceResponse);
    }

    public ResponseResult<List<TerminalServiceResponse>> aroundSearch(String center, Integer radius)
    {
        //?key=4662262223a037bc3c06c5169f2f14af&sid=809394&center=40.02%2C116.41&radius=1000
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.AROUND_SEARCH_URL);
        builder.append("?");
        builder.append("key=" + userKey);
        builder.append("&");
        builder.append("sid=" + sid);
        builder.append("&");
        builder.append("center=" + center);
        builder.append("&");
        builder.append("radius=" + radius);

        log.info(builder.toString());

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        log.info(body);

        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray result = data.getJSONArray("results");
        TerminalServiceResponse terminalServiceResponse = new TerminalServiceResponse();
        ArrayList<TerminalServiceResponse> terminalServiceResponseList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++)
        {
            JSONObject terminal = JSONObject.fromObject(result.get(i));
            //获取车牌号
            String desc = terminal.getString("desc");
            //获取车辆终端号
            String tid = terminal.getString("tid");
            JSONObject location = terminal.getJSONObject("location");
            String latitude = location.getString("latitude");
            String longitude = location.getString("longitude");
            log.info(desc + ',' + tid);
            terminalServiceResponse.setTid(tid);
            terminalServiceResponse.setDesc(desc);
            terminalServiceResponse.setLatitude(latitude);
            terminalServiceResponse.setLongitude(longitude);

            terminalServiceResponseList.add(terminalServiceResponse);
        }
        return ResponseResult.success(terminalServiceResponseList);
    }

    public ResponseResult<TraceSearchServiceResponse> trsearch(String tid, Long starttime, Long endtime)
    {
        //?key=4662262223a037bc3c06c5169f2f14af&sid=809394&tid=594222569&starttime=1668600104183&endtime=1668600278966
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.TRSEARCH_URL);
        builder.append("?");
        builder.append("key=" + userKey);
        builder.append("&");
        builder.append("sid=" + sid);
        builder.append("&");
        builder.append("tid=" + tid);
        builder.append("&");
        builder.append("starttime=" + starttime);
        builder.append("&");
        builder.append("endtime=" + endtime);
        log.info(builder.toString());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.toString(), String.class);
        String body = responseEntity.getBody();
        log.info(body);

        JSONObject bodyJsonObject = JSONObject.fromObject(body);
        JSONObject data = bodyJsonObject.getJSONObject("data");
        JSONArray tracks = data.getJSONArray("tracks");
        Long driveMile = 0L;
        Long driveTime = 0L;
        for (int i = 0; i < tracks.size(); i++)
        {
            JSONObject jsonObject = tracks.getJSONObject(i);
            long distance = jsonObject.getLong("distance");
            long time = jsonObject.getLong("time");
            driveTime += time;
            driveMile += distance;
        }
        driveTime = (driveTime / 1000) / 60;
        log.info(driveMile.toString());
        log.info(driveTime.toString());

        TraceSearchServiceResponse traceSearchServiceResponse = new TraceSearchServiceResponse();
        traceSearchServiceResponse.setDriveMile(driveMile);
        traceSearchServiceResponse.setDriveTime(driveTime);

        return ResponseResult.success(traceSearchServiceResponse);

    }
}
