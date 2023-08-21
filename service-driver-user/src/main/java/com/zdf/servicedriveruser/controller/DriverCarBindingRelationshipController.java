package com.zdf.servicedriveruser.controller;


import com.zdf.internalcommon.dto.DriverCarBindingRelationship;
import com.zdf.internalcommon.dto.ResponseResult;
import com.zdf.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdf
 * @since 2022-09-28
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController
{
    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship)
    {
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unBind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship)
    {
        return driverCarBindingRelationshipService.unBind(driverCarBindingRelationship);
    }

    @GetMapping("/get")
    public ResponseResult getDriverCarBindingRelationship(String driverPhone)
    {
        return driverCarBindingRelationshipService.getDriverCarBindingRelationship(driverPhone);
    }

}
