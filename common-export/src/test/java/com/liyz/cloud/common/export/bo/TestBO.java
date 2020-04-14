package com.liyz.cloud.common.export.bo;

import com.liyz.cloud.common.export.annotation.Export;
import lombok.Getter;
import lombok.Setter;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/14 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Getter
@Setter
public class TestBO implements Serializable {
    private static final long serialVersionUID = 6379378200238185513L;

    @Export(sort = 0, name = "用户id")
    private Integer id;

    @Export(sort = 1, name = "姓名")
    private String name;

    @Export(sort = 2, name = "性别")
    private String sex;

    @Export(sort = 3, name = "年龄")
    private Integer age;
}
