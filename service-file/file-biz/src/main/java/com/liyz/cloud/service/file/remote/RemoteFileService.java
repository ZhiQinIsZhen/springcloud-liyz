package com.liyz.cloud.service.file.remote;

import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.DateUtil;
import com.liyz.cloud.common.model.bo.file.FileInfoBO;
import com.liyz.cloud.common.model.constant.file.FileType;
import com.liyz.cloud.common.model.exception.file.RemoteFileServiceException;
import com.liyz.cloud.service.file.config.FileSnowflakeConfig;
import com.liyz.cloud.service.file.model.FileInfoDO;
import com.liyz.cloud.service.file.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/16 16:31
 */
@Slf4j
@Service
public class RemoteFileService {

    private static final String DEFAULT_ROOT_PATH = "/file";

    @Autowired
    FileInfoService fileInfoService;
    @Autowired
    FileSnowflakeConfig fileSnowflakeConfig;

    /**
     * 上传图片
     *
     * @param fileType
     * @param files
     * @return
     */
    public List<String> upload(FileType fileType, List<FileInfoBO> files) {
        if (fileType == null || files == null || files.size() <= 0) {
            throw new RemoteFileServiceException(CommonCodeEnum.ParameterError);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder path = new StringBuilder();
        List<String> list = new ArrayList<>(files.size());
        for (FileInfoBO file : files) {
            String[] exts = file.getFileName().split("\\.");
            FileInfoDO fileInfoDO = new FileInfoDO();
            fileInfoDO.setFileKey(String.valueOf(fileSnowflakeConfig.getId()));
            fileInfoDO.setFileName(file.getFileName());
            fileInfoDO.setFileContentType(file.getFileContentType());
            fileInfoDO.setFileType(fileType.getCode());
            fileInfoDO.setFileExt(exts[exts.length-1]);
            fileInfoDO.setCreateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
            fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
            //判断文件是否已经存在
            String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
            FileInfoDO fileDO = fileInfoService.getByMd5(fileMd5);
            if (Objects.isNull(fileDO)) {
                path.append(DEFAULT_ROOT_PATH).append(fileType.getSubPath()).append(localDateTime.getYear()).append("/")
                        .append(localDateTime.getMonthValue()).append("/").append(localDateTime.getDayOfMonth());
                File upFile = new File(path.toString());
                upFile.setWritable(true, false);
                if (!upFile.exists()) {
                    upFile.mkdirs();
                }
                path.append("/").append(fileInfoDO.getFileKey()).append(".").append(fileInfoDO.getFileExt());
                File dest = new File(path.toString());
                try {
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(file.getBytes()), dest);
                } catch (IOException e) {
                    log.error("save file fail error : ", e);
                }
            } else {
                path.append(fileDO.getFilePath());
            }
            fileInfoDO.setFileMd5(fileMd5);
            fileInfoDO.setFilePath(path.toString());
            fileInfoService.save(fileInfoDO);
            path.setLength(0);
            list.add(fileInfoDO.getFileKey());
        }
        return list;
    }

    /**
     * 下载图片
     *
     * @param fileInfoBO
     * @return
     */
    public FileInfoBO download(FileInfoBO fileInfoBO) {
        FileInfoDO fileInfoDO = fileInfoService.getOne(CommonConverterUtil.beanCopy(fileInfoBO, FileInfoDO.class));
        if (fileInfoDO == null) {
            throw new RemoteFileServiceException(CommonCodeEnum.NoData);
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(fileInfoDO.getFilePath()));
            FileInfoBO result = CommonConverterUtil.beanCopy(fileInfoDO, FileInfoBO.class);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int len;
            while((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            fis.close();
            result.setBytes(bos.toByteArray());
            return result;
        } catch (Exception e) {
            log.error("download fail error", e);
            throw new RemoteFileServiceException(CommonCodeEnum.NoData);
        }
    }

    /**
     * 删除图片
     *
     * @param fileInfoBO
     */
    public void delete(FileInfoBO fileInfoBO) {
        //这里的删除为逻辑删除，非物理删除
        FileInfoDO fileInfoDO = new FileInfoDO();
        fileInfoDO.setFileKey(fileInfoBO.getFileKey());
        fileInfoDO.setFileType(fileInfoBO.getFileType());
        fileInfoDO.setIsInactive(1);
        fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(LocalDateTime.now()));
        fileInfoService.updateById(fileInfoDO);
    }

    /**
     * 修改图片
     *
     * @param fileInfoBO
     * @return
     */
    public String update(FileInfoBO fileInfoBO) {
        FileType fileType = FileType.getByCode(fileInfoBO.getFileType());
        FileInfoDO old = fileInfoService.getById(fileInfoBO.getFileKey());
        if (Objects.isNull(old)) {
            throw new RemoteFileServiceException(CommonCodeEnum.OldFileNotExsist);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder path = new StringBuilder();
        String[] exts = fileInfoBO.getFileName().split("\\.");
        FileInfoDO fileInfoDO = new FileInfoDO();
        fileInfoDO.setFileKey(String.valueOf(fileSnowflakeConfig.getId()));
        fileInfoDO.setFileName(fileInfoBO.getFileName());
        fileInfoDO.setFileContentType(fileInfoBO.getFileContentType());
        fileInfoDO.setFileType(fileType.getCode());
        fileInfoDO.setFileExt(exts[exts.length-1]);
        fileInfoDO.setCreateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
        fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
        //判断文件是否已经存在
        String fileMd5 = DigestUtils.md5DigestAsHex(fileInfoBO.getBytes());
        FileInfoDO fileDO = fileInfoService.getByMd5(fileMd5);
        if (Objects.isNull(fileDO)) {
            path.append(DEFAULT_ROOT_PATH).append(fileType.getSubPath()).append(localDateTime.getYear()).append("/")
                    .append(localDateTime.getMonthValue()).append("/").append(localDateTime.getDayOfMonth());
            File upFile = new File(path.toString());
            upFile.setWritable(true, false);
            if (!upFile.exists()) {
                upFile.mkdirs();
            }
            path.append("/").append(fileInfoDO.getFileKey()).append(".").append(fileInfoDO.getFileExt());
            File dest = new File(path.toString());
            try {
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(fileInfoBO.getBytes()), dest);
            } catch (IOException e) {
                log.error("save file fail error : ", e);
            }
        } else {
            path.append(fileDO.getFilePath());
        }
        fileInfoDO.setFilePath(path.toString());
        FileInfoDO newDO = CommonConverterUtil.beanCopy(old, FileInfoDO.class);
        newDO.setIsInactive(1);
        newDO.setFileKey(fileInfoDO.getFileKey());
        fileInfoService.save(newDO);

        fileInfoDO.setFileMd5(fileMd5);
        fileInfoDO.setFileKey(old.getFileKey());
        fileInfoDO.setCreateTime(old.getCreateTime());
        fileInfoService.updateById(fileInfoDO);
        return fileInfoDO.getFileKey();
    }
}
