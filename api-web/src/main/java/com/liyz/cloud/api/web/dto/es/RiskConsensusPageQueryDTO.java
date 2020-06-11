package com.liyz.cloud.api.web.dto.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyz.cloud.api.web.dto.page.PageBaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/16 15:12
 */
@Data
public class RiskConsensusPageQueryDTO extends PageBaseDTO {
    private static final long serialVersionUID = -3549952111209773867L;

    private String keyWord;

    private String sentimentType;

    private String sourceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
}
