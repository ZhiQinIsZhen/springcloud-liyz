package com.liyz.cloud.service.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 15:53
 */
@Data
@Document(indexName = "nearby", type = "wechat")
public class NearByDO implements Serializable {
    private static final long serialVersionUID = -6969232713894878668L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String wxNo;

    @Field(type = FieldType.Keyword)
    private String nickName;

    @Field(type = FieldType.Keyword)
    private String sex;

//    @Field(format = DateFormat.date_hour_minute_second)
//    private Date createTime;
}
