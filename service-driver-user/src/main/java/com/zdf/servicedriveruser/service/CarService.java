package com.zdf.servicedriveruser.service;

import com.zdf.internalcommon.dto.Car;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.internalcommon.response.TerminalServiceResponse;
import com.zdf.internalcommon.response.TraceResponse;
import com.zdf.servicedriveruser.mapper.CarMapper;
import com.zdf.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService
{
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult insertCar(Car car)
    {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        //创建终端
        ResponseResult<TerminalServiceResponse> terminalServiceResponseResponseResult = serviceMapClient.addTerminal(car.getVehicleNo(), car.getId() + "");
        TerminalServiceResponse terminalServiceResponse = terminalServiceResponseResponseResult.getData();
        String tid = terminalServiceResponse.getTid();
        //创建轨迹
        ResponseResult<TraceResponse> traceResponseResponseResult = serviceMapClient.addTrace(tid);
        TraceResponse traceResponse = traceResponseResponseResult.getData();
        String trid = traceResponse.getTrid();
        String trname = traceResponse.getTrname();
        car.setTid(tid);
        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.updateById(car);

        return ResponseResult.success("1");
    }

    public ResponseResult<Car> getCar(Long carid)
    {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", carid);
        List<Car> cars = carMapper.selectByMap(queryMap);
        Car car = cars.get(0);
        return ResponseResult.success(car);
    }
}
