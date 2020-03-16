package com.liyz.cloud.service.file.service;

import com.liyz.cloud.common.dao.service.AbstractService;
import com.liyz.cloud.service.file.model.FileInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/25 17:32
 */
@Slf4j
@Service
public class FileInfoService extends AbstractService<FileInfoDO> {

    public FileInfoDO getByMd5(String fileMd5) {
        FileInfoDO param = new FileInfoDO();
        param.setFileMd5(fileMd5);
        param.setIsInactive(null);
        return mapper.selectOne(param);
    }
}
