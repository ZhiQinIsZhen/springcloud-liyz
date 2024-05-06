package com.liyz.cloud.api.staff.controller.staff;

import com.liyz.cloud.api.staff.feign.staff.FeignStaffInfoService;
import com.liyz.cloud.api.staff.vo.staff.StaffInfoVO;
import com.liyz.cloud.common.api.dto.PageDTO;
import com.liyz.cloud.common.base.page.PageBO;
import com.liyz.cloud.common.base.page.RemotePage;
import com.liyz.cloud.common.base.result.PageResult;
import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.common.base.util.BeanUtil;
import com.liyz.cloud.common.exception.CommonExceptionCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/16 16:38
 */
@Tag(name = "员工信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffInfoController {

    @Resource
    private FeignStaffInfoService feignStaffInfoService;

    @Operation(summary = "查询当前登录员工信息")
    @GetMapping("/current")
    public Result<StaffInfoVO> userInfo() {
        return feignStaffInfoService.getByStaffId(1L);
    }

    @Operation(summary = "分页查询员工信息")
    @GetMapping("/page")
    public PageResult<StaffInfoVO> page(PageDTO page) {
        Result<RemotePage<StaffInfoVO>> pageResult = feignStaffInfoService.page(BeanUtil.copyProperties(page, PageBO::new));
        if (CommonExceptionCodeEnum.SUCCESS.getCode().equals(pageResult.getCode())) {
            return PageResult.success(pageResult.getData());
        }
        return PageResult.error(pageResult.getCode(), pageResult.getMessage());
    }
}
