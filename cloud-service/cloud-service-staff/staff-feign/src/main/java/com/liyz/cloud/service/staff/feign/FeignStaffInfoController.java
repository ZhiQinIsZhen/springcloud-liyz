package com.liyz.cloud.service.staff.feign;

import com.liyz.cloud.service.staff.service.StaffInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 13:37
 */
@Tag(name = "客户信息")
@RestController
@RequestMapping("/info")
public class FeignStaffInfoController {

    @Resource
    private StaffInfoService staffInfoService;


}
