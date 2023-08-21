package com.zdf.apidriver.service;

import com.zdf.apidriver.remote.ServiceDriverUserClient;
import com.zdf.apidriver.remote.ServiceMapClient;
import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.Point;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.request.ApiDriverPointRequest;
import com.zdf.internalcommon.request.PointServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PointService
{
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;
    @Autowired
    private ServiceMapClient serviceMapClient;
    public ResponseResult uploadPoint(ApiDriverPointRequest apiDriverPointRequest)
    {
        //获取carid
        Long carid = apiDriverPointRequest.getCarid();
        //根据carid获取tid和trid
        ResponseResult<Car> responseResult = serviceDriverUserClient.getCar(carid);
        Car car = responseResult.getData();
        String tid = car.getTid();
        String trid = car.getTrid();
        log.info(tid);
        log.info(trid);
        //上传轨迹
        Point[] points = apiDriverPointRequest.getPoints();
        PointServiceRequest pointServiceRequest = new PointServiceRequest();
        pointServiceRequest.setPoints(points);
        pointServiceRequest.setTid(tid);
        pointServiceRequest.setTrid(trid);

        ResponseResult<String> stringResponseResult = serviceMapClient.uploadPoint(pointServiceRequest);
        String data = stringResponseResult.getData();
        return ResponseResult.success(data);
    }
}
