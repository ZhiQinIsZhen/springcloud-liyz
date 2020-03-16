package com.liyz.cloud.common.model.bo.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 17:12
 */
@Data
public class FileInfoBO implements Serializable {
    private static final long serialVersionUID = 4002855402379971257L;

    private byte[] bytes;

    private String fileContentType;

    private String fileName;

    private String fileKey;

    private Integer fileType;

    private Integer isInactive = 0;
}
