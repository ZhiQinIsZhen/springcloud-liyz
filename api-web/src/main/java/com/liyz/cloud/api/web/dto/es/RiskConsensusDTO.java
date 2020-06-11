package com.liyz.cloud.api.web.dto.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/16 16:33
 */
@Data
public class RiskConsensusDTO implements Serializable {
    private static final long serialVersionUID = -2006229914007431754L;

    private Long id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String finalTitle;

    private String name;

    private String pickedAbstract;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    private String remark;

    private Double sentimentScore;

    private Integer sentimentType;

    private String source;

    private String sourceType;

    private String title;

    private String webSite;
}
