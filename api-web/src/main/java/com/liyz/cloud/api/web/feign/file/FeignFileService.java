package com.liyz.cloud.api.web.feign.file;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.file.FileInfoBO;
import com.liyz.cloud.common.model.bo.file.FileInfoListBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/16 17:05
 */
@FeignClient(value = "service-file")
@RequestMapping("/file")
public interface FeignFileService {

    @PostMapping(value = "/upload", consumes = "application/json")
    Result<List<String>> upload(@RequestBody FileInfoListBO fileInfoListBO);

    @GetMapping("/download")
    Result<FileInfoBO> download(FileInfoBO fileInfoBO);

    @PostMapping(value = "/delete", consumes = "application/json")
    Result delete(@RequestBody FileInfoBO fileInfoBO);

    @PostMapping(value = "/update", consumes = "application/json")
    Result<String> update(@RequestBody FileInfoBO fileInfoBO);
}
