package com.zdf.servicemap.remote;

import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.MapConfigConstant;
import com.zdf.internalcommon.dto.DicDistrict;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicemap.mapper.DicDistrictMapper;
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
public class MapDicDistrictClient
{
    @Value("${map.key}")
    private String userKey;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DicDistrictMapper dicDistrictMapper;


    public ResponseResult initDictDistrict(String keywords)
    {
        //?keywords=北京&subdistrict=2&key=<用户的key>
        //拼接url
        StringBuilder builder = new StringBuilder();
        builder.append(MapConfigConstant.DISTRICT_URL);
        builder.append("?");
        builder.append("keywords=").append(keywords);
        builder.append("&");
        builder.append("subdistrict=3");
        builder.append("&");
        builder.append("key=").append(userKey);
        log.info(builder.toString());
        //请求服务
        ResponseEntity<String> forEntity = restTemplate.getForEntity(builder.toString(), String.class);
        String forEntityString = forEntity.getBody();
        log.info(forEntityString);

        //解析返回结果
        JSONObject jsonObject = JSONObject.fromObject(forEntityString);
        int status = jsonObject.getInt(MapConfigConstant.STATUS);
        if (status != 1)
        {
            return ResponseResult.fail(CommonStatusEnum.DISTRICT_ERROR.getCode(), CommonStatusEnum.DISTRICT_ERROR.getMessage());
        }
        //解析国家字段
        JSONArray countryJsonArray = jsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++)
        {
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            String countryCode = countryJsonObject.getString(MapConfigConstant.ADCODE);
            String countryName = countryJsonObject.getString(MapConfigConstant.NAME);
            String coLevel = countryJsonObject.getString(MapConfigConstant.LEVEL);
            int countrylevel = generateLevel(coLevel);
            String countryParentCode = "0";
            insertValue(countryCode, countryName, countryParentCode, countrylevel);
            //解析省会字段
            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
            for (int province = 0; province < provinceJsonArray.size(); province++)
            {
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(province);
                String provinceCode = provinceJsonObject.getString(MapConfigConstant.ADCODE);
                String provinceName = provinceJsonObject.getString(MapConfigConstant.NAME);
                String pLevel = provinceJsonObject.getString(MapConfigConstant.LEVEL);
                int provinceLevel = generateLevel(pLevel);
                insertValue(provinceCode, provinceName, countryCode, provinceLevel);
                
                //解析城市字段
                JSONArray cityJsonArray = provinceJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
                for (int city = 0; city < cityJsonArray.size(); city++)
                {
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(city);
                    String cityCode = cityJsonObject.getString(MapConfigConstant.ADCODE);
                    String cityName = cityJsonObject.getString(MapConfigConstant.NAME);
                    String ciLevel = cityJsonObject.getString(MapConfigConstant.LEVEL);
                    int cityLevel = generateLevel(ciLevel);
                    insertValue(cityCode, cityName, provinceCode, cityLevel);
                    //解析区县字段
                    JSONArray districtsJsonArray = cityJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
                    for (int district = 0; district < districtsJsonArray.size(); district++)
                    {
                        JSONObject districtJsonObject = districtsJsonArray.getJSONObject(district);
                        String districtCode = districtJsonObject.getString(MapConfigConstant.ADCODE);
                        String districtName = districtJsonObject.getString(MapConfigConstant.NAME);
                        String diLevel = districtJsonObject.getString(MapConfigConstant.LEVEL);
                        if (diLevel.trim().equals("street".trim()))
                        {
                            continue;
                        }
                        int districtLevel = generateLevel(diLevel);

                        insertValue(districtCode, districtName, cityCode, districtLevel);

                    }

                }

            }

        }
        //插入数据库
        return null;
    }

    private int generateLevel(String level)
    {
        int levelResult = -1;
        if (level.trim().equals("country".trim()))
        {
            levelResult = 0;
        }
        else if (level.trim().equals("province".trim()))
        {
            levelResult = 1;
        }
        else if (level.trim().equals("city".trim()))
        {
            levelResult = 2;
        }
        else if (level.trim().equals("district".trim()))
        {
            levelResult = 3;
        }
        return levelResult;
    }

    private void insertValue(String acode, String name, String parentCode, int level)
    {
        DicDistrict dicDistrict = new DicDistrict();

        dicDistrict.setAddressCode(acode);
        dicDistrict.setAddressName(name);
        dicDistrict.setParentAddressCode(parentCode);
        dicDistrict.setLevel(level);

        dicDistrictMapper.insert(dicDistrict);
    }
}
