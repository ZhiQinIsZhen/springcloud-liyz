package com.liyz.cloud.service.es.controller;

import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultBO;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultPageQueryBO;
import com.liyz.cloud.service.es.remote.RemoteJudgementResultService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/29 13:19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RemoteJudgementResultService.class})
public class RemoteJudgementResultServiceTest {

    @Autowired
    RemoteJudgementResultService remoteJudgementResultService;

    @Test
    public void searchTest() {
        JudgementResultPageQueryBO queryBO = new JudgementResultPageQueryBO();
        queryBO.setKeyWord("北京蓝耘科技股份有限公司");
        queryBO.setPageNum(1);
        queryBO.setPageSize(10);
        Page<JudgementResultBO> page = remoteJudgementResultService.search(queryBO);
        log.info("page : {}", page.getTotalElements());
    }
}