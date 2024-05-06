package com.liyz.cloud.api.staff.feign.staff;

import com.liyz.cloud.api.staff.vo.staff.StaffInfoVO;
import com.liyz.cloud.common.base.page.PageBO;
import com.liyz.cloud.common.base.page.RemotePage;
import com.liyz.cloud.common.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 17:47
 */
@FeignClient(value = "cloud-service-staff", contextId = "StaffInfo", path = "/staff/info")
public interface FeignStaffInfoService {

    @Operation(summary = "根据staffId获取用户信息")
    @GetMapping("/getByStaffId")
    Result<StaffInfoVO> getByStaffId(@RequestParam("staffId") Long staffId);

    @Operation(summary = "分页查询员工信息")
    @GetMapping("/page")
    Result<RemotePage<StaffInfoVO>> page(PageBO pageBO);
}
