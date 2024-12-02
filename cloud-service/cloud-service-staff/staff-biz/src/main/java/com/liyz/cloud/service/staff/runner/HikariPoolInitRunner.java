package com.liyz.cloud.service.staff.runner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liyz.cloud.service.staff.model.StaffLoginLogDO;
import com.liyz.cloud.service.staff.service.StaffLoginLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Desc:数据库连接池初始化
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/14 9:19
 */
@Slf4j
@Component
public class HikariPoolInitRunner implements ApplicationRunner {

    @Resource
    private StaffLoginLogService staffLoginLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StaffLoginLogDO staffLoginLogDO = staffLoginLogService.getOne(
                Wrappers.lambdaQuery(StaffLoginLogDO.builder().staffId(1L).build()), false);
        log.info("hikari pool init success");
    }
}
