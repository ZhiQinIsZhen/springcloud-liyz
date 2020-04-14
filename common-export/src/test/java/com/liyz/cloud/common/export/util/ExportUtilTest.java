package com.liyz.cloud.common.export.util;

import com.liyz.cloud.common.export.bo.TestBO;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/14 10:11
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExportUtil.class})
public class ExportUtilTest {

    @Test
    public void csvExportTest() throws Exception {
        TestBO testBO = new TestBO();
        testBO.setId(1);
        testBO.setName("张三");
        testBO.setSex("男");
        testBO.setAge(22);
        ExportUtil.exportCsv("公司成员", "C:\\Users\\liyangzhen\\Documents", Lists.newArrayList(testBO), TestBO.class);
    }
}