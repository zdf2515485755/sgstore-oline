package com.zdf.servicepassengeruser.service;

import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.constant.PassengerConstant;
import com.zdf.internalcommon.dto.PassengerUser;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private PassengerUserMapper passengerUserMapper;

    /**
     * 根据电话号码查询用户，不存在就插入
     * @param passengerPhone
     * @return
     */
    public ResponseResult loginOrRegister(String passengerPhone)
    {
        System.out.println("phone:" + passengerPhone);
        //根据电话号码查询用户
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerList = passengerUserMapper.selectByMap(map);
        //不存在就插入数据库
        if(passengerList.size() == 0)
        {
            PassengerUser user = new PassengerUser();
            LocalDateTime now = LocalDateTime.now();
            user.setState(PassengerConstant.PASSENGER_STATE_VAILD);
            user.setPassengerName("zhangsan");
            user.setPassengerPhone(passengerPhone);
            user.setGmtCreate(now);
            user.setGmtModified(now);
            user.setPassengerGender(PassengerConstant.PASSENGER_GENDER_WOMAN);

            passengerUserMapper.insert(user);

        }
        return ResponseResult.success("");
    }

    /**
     * 根据电话号码查询用户信息返回
     * @param passengerPhone
     * @return
     */

    public ResponseResult getUserByPhone(String passengerPhone)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerList = passengerUserMapper.selectByMap(map);
        if (passengerList.size() == 0)
        {
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(), CommonStatusEnum.USER_NOT_EXIST.getMessage());
        }
        else
        {
            PassengerUser passengerUser = passengerList.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
