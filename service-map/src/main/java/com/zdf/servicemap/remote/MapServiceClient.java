package com.zdf.servicemap.remote;


import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.MapServiceResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapServiceClient
{
    @Value("${map.key}")
    private String userKey;
    @Autowired
    RestTemplate restTemplate;

   public ResponseResult addService(String name)
   {
       //key=4662262223a037bc3c06c5169f2f14af&name=OnlineTaxiService
       StringBuilder builder = new StringBuilder();
       builder.append(MapConfigConstant.MAP_SERVICE_ADD_URL);
       builder.append("?");
       builder.append("key=").append(userKey);
       builder.append("&");
       builder.append("name=").append(name);

       log.info(builder.toString());
       ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
       String body = stringResponseEntity.getBody();
       JSONObject jsonObject = JSONObject.fromObject(body);
       JSONObject data = jsonObject.getJSONObject("data");
       String sid = data.getString("sid");
       MapServiceResponse mapServiceResponse = new MapServiceResponse();
       mapServiceResponse.setSid(sid);

       return ResponseResult.success(mapServiceResponse);
   }
}
