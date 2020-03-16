package com.liyz.cloud.service.file.controller;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.file.FileInfoBO;
import com.liyz.cloud.common.model.bo.file.FileInfoListBO;
import com.liyz.cloud.service.file.remote.RemoteFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/16 16:30
 */
@Api(value = "文件处理", tags = "文件处理")
@RestController
@RequestMapping("/file")
public class FeignFileController {

    @Autowired
    RemoteFileService remoteFileService;

    @PostMapping(value = "/upload", consumes = "application/json")
    public Result<List<String>> upload(@RequestBody FileInfoListBO fileInfoListBO) {
        return Result.success(remoteFileService.upload(fileInfoListBO.getFileType(), fileInfoListBO.getFiles()));
    }

    @GetMapping("/download")
    public Result<FileInfoBO> download(FileInfoBO fileInfoBO) {
        return Result.success(remoteFileService.download(fileInfoBO));
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public Result delete(@RequestBody FileInfoBO fileInfoBO) {
        remoteFileService.delete(fileInfoBO);
        return Result.success();
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public Result<String> update(@RequestBody FileInfoBO fileInfoBO) {
        return Result.success(remoteFileService.update(fileInfoBO));
    }
}
