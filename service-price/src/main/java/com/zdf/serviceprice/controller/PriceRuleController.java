package com.zdf.serviceprice.controller;


import com.zdf.internalcommon.dto.PriceRule;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdf
 * @since 2022-10-18
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController
{
    @Autowired
    private PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult addPriceRule(@RequestBody PriceRule priceRule)
    {
        return priceRuleService.addPriceRule(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult editPriceRule(@RequestBody PriceRule priceRule)
    {
        return priceRuleService.editPriceRule(priceRule);
    }

    @GetMapping("/get")
    public ResponseResult getPriceRule(@RequestParam String fareType)
    {
        return priceRuleService.getPriceRule(fareType);
    }

    @GetMapping("/is-new")
    public ResponseResult isNewPriceRule(@RequestParam String fareType, @RequestParam Integer fareVersion)
    {
        return priceRuleService.isNewPriceRule(fareType, fareVersion);
    }

    @PostMapping("/is-exist")
    public ResponseResult isExist(@RequestBody PriceRule priceRule)
    {
        return priceRuleService.isExist(priceRule);
    }


}
