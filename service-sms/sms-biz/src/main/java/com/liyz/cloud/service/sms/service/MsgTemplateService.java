package com.liyz.cloud.service.sms.service;

import com.liyz.cloud.common.dao.service.AbstractService;
import com.liyz.cloud.service.sms.model.MsgTemplateDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/11 16:54
 */
@Slf4j
@Service
public class MsgTemplateService extends AbstractService<MsgTemplateDO> {

    @Override
    public MsgTemplateDO getOne(MsgTemplateDO model) {
        return super.getOne(model);
    }
}
