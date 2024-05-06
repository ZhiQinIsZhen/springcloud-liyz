package com.liyz.cloud.service.staff.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.cloud.common.base.page.PageBO;
import com.liyz.cloud.common.base.page.RemotePage;
import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.common.base.util.BeanUtil;
import com.liyz.cloud.service.staff.bo.StaffInfoBO;
import com.liyz.cloud.service.staff.model.StaffInfoDO;
import com.liyz.cloud.service.staff.service.StaffInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Operation(summary = "根据staffId获取用户信息")
    @GetMapping("/getByStaffId")
    public Result<StaffInfoBO> getByStaffId(@RequestParam("staffId") Long staffId) {
        return Result.success(BeanUtil.copyProperties(staffInfoService.getById(staffId), StaffInfoBO::new));
    }

    @Operation(summary = "分页查询员工信息")
    @GetMapping("/page")
    public Result<RemotePage<StaffInfoBO>> page(PageBO pageBO) {
        Page<StaffInfoDO> page = staffInfoService.page(Page.of(pageBO.getPageNum(), pageBO.getPageSize()));
        RemotePage<StaffInfoBO> remotePage = RemotePage.of(
                BeanUtil.copyList(page.getRecords(), StaffInfoBO::new),
                page.getTotal(),
                pageBO.getPageNum(),
                pageBO.getPageSize()
        );
        return Result.success(remotePage);
    }
}
