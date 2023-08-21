package com.zdf.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.internalcommon.constant.CommonStatusEnum;
import com.zdf.internalcommon.dto.PriceRule;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.serviceprice.mapper.PriceRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdf
 * @since 2022-10-18
 */
@Service
@Slf4j
public class PriceRuleService
{
    @Autowired
    private PriceRuleMapper priceRuleMapper;
    public ResponseResult addPriceRule(PriceRule priceRule)
    {
        //先查询表中是否有记录，没有才插入
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
        priceRuleQueryWrapper.eq("city_code", cityCode);
        priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
        priceRuleQueryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
        int fareVersion = 0;
        if (!priceRules.isEmpty())
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_EXIST.getMessage(), "");
        }
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("1");
    }

    public ResponseResult editPriceRule(PriceRule priceRule)
    {
        //先查询表中是否有记录，有才更新
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
        priceRuleQueryWrapper.eq("city_code", cityCode);
        priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
        priceRuleQueryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
        if (priceRules.size() == 0)
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage(), "");
        }
        PriceRule priceRuleResult = priceRules.get(0);
        if (priceRule.getUnitPricePerMinute().doubleValue() == priceRuleResult.getUnitPricePerMinute().doubleValue()
                && priceRule.getUnitPricePerMile().doubleValue() == priceRuleResult.getUnitPricePerMile().doubleValue()
                && priceRule.getStartMile().intValue() == priceRuleResult.getStartMile().intValue()
                && priceRule.getStartFare().doubleValue() == priceRuleResult.getStartFare().doubleValue()
        )
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NO_CHANGE.getCode(), CommonStatusEnum.PRICE_RULE_NO_CHANGE.getMessage(), "");
        }
        String fareType = cityCode + "$" + vehicleType;
        Integer fareVersion = priceRuleResult.getFareVersion();
        priceRule.setFareType(fareType);
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("1");
    }

    public ResponseResult getPriceRule(@RequestParam String fareType)
    {
        QueryWrapper<PriceRule> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("fare_type", fareType);
        QueryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(QueryWrapper);
        if (priceRules.isEmpty())
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage());
        }
        PriceRule priceRule = priceRules.get(0);
        return ResponseResult.success(priceRule);
    }

    public ResponseResult isNewPriceRule(String fareType, Integer fareVersion)
    {
        ResponseResult<PriceRule> responseResult = getPriceRule(fareType);
        if (responseResult.getCode() == CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode())
        {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage());
        }
        PriceRule data = responseResult.getData();
        Integer fareVersionDB = data.getFareVersion();
        if (fareVersionDB > fareVersion)
        {
            return ResponseResult.success(false);
        }
        else
        {
            return ResponseResult.success(true);
        }
    }
    
    public ResponseResult isExist(@RequestBody PriceRule priceRule)
    {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        Integer count = priceRuleMapper.selectCount(queryWrapper);
        if (count > 0)
        {
            return  ResponseResult.success(true);
        }
        else
        {
            return ResponseResult.success(false);
        }

    }

}
