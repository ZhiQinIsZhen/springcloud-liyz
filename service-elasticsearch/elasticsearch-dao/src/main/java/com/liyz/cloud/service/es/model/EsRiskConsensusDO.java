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
 * @date 2020/1/13 11:31
 */
@Data
@Document(indexName = "risk_consensus", type = "_doc")
public class EsRiskConsensusDO implements Serializable {
    private static final long serialVersionUID = -879485120284773650L;

    @Id
    private Long id;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String content;

    private String createTime;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String finalTitle;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String pickedAbstract;

    private String publishTime;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String remark;

    @Field(type = FieldType.Float)
    private Double sentimentScore;

    @Field(type = FieldType.Integer)
    private Integer sentimentType;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Keyword)
    private String sourceType;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String webSite;
}
