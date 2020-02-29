package com.liyz.cloud.api.web.dto.es;

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

    private Date beginTime;

    private Date endTime;
}
