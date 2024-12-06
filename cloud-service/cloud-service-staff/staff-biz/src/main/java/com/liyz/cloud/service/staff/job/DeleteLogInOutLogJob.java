package com.liyz.cloud.service.staff.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liyz.cloud.common.util.DateUtil;
import com.liyz.cloud.service.staff.model.StaffLoginLogDO;
import com.liyz.cloud.service.staff.model.StaffLogoutLogDO;
import com.liyz.cloud.service.staff.service.StaffLoginLogService;
import com.liyz.cloud.service.staff.service.StaffLogoutLogService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/15 10:55
 */
@Slf4j
@Component
public class DeleteLogInOutLogJob {

    private static final int DEFAULT_LOG_DELETE_DAY = 30;

    @Resource
    private StaffLoginLogService staffLoginLogService;
    @Resource
    private StaffLogoutLogService staffLogoutLogService;

    @XxlJob(value = "deleteLoginLogJob")
    public ReturnT<String> deleteLoginLogJob() {
        long startTime = System.currentTimeMillis();
        XxlJobHelper.log("删除30天外的登录日志开始...");
        Date minDate = DateUtil.offsetMonth(DateUtil.beginOfDay(DateUtil.date()), -1);
        staffLoginLogService.remove(Wrappers.lambdaQuery(StaffLoginLogDO.class).le(StaffLoginLogDO::getLoginTime, minDate));
        XxlJobHelper.log("删除30天外的登录日志结束...耗时: {}ms",
                System.currentTimeMillis() - startTime);
        return ReturnT.SUCCESS;
    }

    @XxlJob(value = "deleteLogoutLogJob")
    public ReturnT<String> deleteLogoutLogJob() {
        long startTime = System.currentTimeMillis();
        XxlJobHelper.log("删除30天外的登出日志开始...");
        Date minDate = DateUtil.offsetMonth(DateUtil.beginOfDay(DateUtil.date()), -1);
        staffLogoutLogService.remove(Wrappers.lambdaQuery(StaffLogoutLogDO.class).le(StaffLogoutLogDO::getLogoutTime, minDate));
        XxlJobHelper.log("删除30天外的登出日志结束...耗时: {}ms",
                System.currentTimeMillis() - startTime);
        return ReturnT.SUCCESS;
    }
}
