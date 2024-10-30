package com.liyz.cloud.service.staff.controller.info;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.cloud.common.base.util.BeanUtil;
import com.liyz.cloud.common.feign.bo.RemotePage;
import com.liyz.cloud.common.feign.dto.PageDTO;
import com.liyz.cloud.common.feign.result.Result;
import com.liyz.cloud.service.staff.bo.info.StaffInfoBO;
import com.liyz.cloud.service.staff.constants.StaffConstants;
import com.liyz.cloud.service.staff.model.StaffInfoDO;
import com.liyz.cloud.service.staff.service.StaffInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 13:37
 */
@Tag(name = "客户信息")
@RestController
@RequestMapping(StaffConstants.STAFF_INFO_URL)
public class StaffInfoController {

    @Resource
    private StaffInfoService staffInfoService;

    @Operation(summary = "根据staffId获取用户信息")
    @GetMapping("/getByStaffId")
    public Result<StaffInfoBO> getByStaffId(@RequestParam("staffId") Long staffId) {
        return Result.success(BeanUtil.copyProperties(staffInfoService.getById(staffId), StaffInfoBO::new));
    }

    @Operation(summary = "分页查询员工信息")
    @PostMapping("/page")
    public Result<RemotePage<StaffInfoBO>> page(@RequestBody PageDTO pageDTO) {
        Page<StaffInfoDO> page = staffInfoService.page(Page.of(pageDTO.getPageNum(), pageDTO.getPageSize()));
        RemotePage<StaffInfoBO> remotePage = RemotePage.of(
                BeanUtil.copyList(page.getRecords(), StaffInfoBO::new),
                page.getTotal(),
                pageDTO.getPageNum(),
                pageDTO.getPageSize()
        );
        return Result.success(remotePage);
    }
}
