package com.liyz.cloud.api.web.controller.file;

import com.liyz.cloud.api.web.dto.file.FileDTO;
import com.liyz.cloud.api.web.feign.file.FeignFileService;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.controller.util.HttpRequestUtil;
import com.liyz.cloud.common.model.bo.file.FileInfoBO;
import com.liyz.cloud.common.model.bo.file.FileInfoListBO;
import com.liyz.cloud.common.model.constant.file.FileType;
import com.liyz.cloud.common.security.annotation.Anonymous;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/16 17:07
 */
@Api(value = "文件服务", tags = "文件服务")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FeignFileService feignFileService;

    @ApiOperation(value = "批量上传图片,支持JPG、PNG、JPEG、BMP", notes = "type说明：0：上传头像，1：身份认证，3：banner图片，4：logo")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/upload")
    public Result upload(@ApiParam(value = "0", required = true) Integer fileType,
                         @RequestParam("files") MultipartFile[] files) throws IOException {
        if (fileType == null || files == null || files.length <= 0) {
            return Result.error(CommonCodeEnum.ParameterError);
        }
        FileType type = FileType.getByCode(fileType);
        if (type == null) {
            return Result.error(CommonCodeEnum.ParameterError);
        }
        List<FileInfoBO> boList = new ArrayList<>(files.length);
        for (MultipartFile multipartFile : files) {
            FileInfoBO fileInfoBO = new FileInfoBO();
            fileInfoBO.setFileContentType(multipartFile.getContentType());
            fileInfoBO.setFileName(multipartFile.getOriginalFilename());
            fileInfoBO.setBytes(multipartFile.getBytes());
            boList.add(fileInfoBO);
        }
        FileInfoListBO listBO = new FileInfoListBO();
        listBO.setFileType(type);
        listBO.setFiles(boList);
        return feignFileService.upload(listBO);
    }

    @Anonymous
    @ApiOperation(value = "下载文件", notes = "type说明：0：上传头像，1：身份认证，3：banner图片，4：logo")
    @GetMapping("/down")
    public Result download(@Validated({FileDTO.File.class}) FileDTO fileDTO) {
        HttpServletResponse response = HttpRequestUtil.getResponse();
        Result<FileInfoBO> boResult = feignFileService.download(CommonConverterUtil.beanCopy(fileDTO, FileInfoBO.class));
        if (!CommonCodeEnum.success.getCode().equals(boResult.getCode())) {
            return Result.error(boResult.getCode(), boResult.getMessage());
        }
        FileInfoBO fileInfoBO = boResult.getData();
        if (fileInfoBO != null) {
            response.setContentType(fileInfoBO.getFileContentType());
            response.setCharacterEncoding("utf-8");
            InputStream inputStream;
            try {
                response.setHeader("Content-Disposition", "inline;fileName=" + URLEncoder.encode(fileInfoBO.getFileName(), "UTF-8"));
                OutputStream nos = response.getOutputStream();
                inputStream = new ByteArrayInputStream(fileInfoBO.getBytes());
                int len;
                byte[] buffer = new byte[2048];
                while ((len = inputStream.read(buffer)) != -1) {
                    nos.write(buffer, 0, len);
                }
                nos.flush();
            } catch (IOException e) {
                log.error("download file write fail", e);
            }
        }
        return Result.success();
    }

    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "删除文件", notes = "type说明：0：上传头像，1：身份认证，3：banner图片，4：logo")
    @DeleteMapping("/delete")
    public Result delete(@Validated({FileDTO.File.class}) FileDTO fileDTO) {
        FileType type = FileType.getByCode(fileDTO.getFileType());
        if (type == null) {
            return Result.error(CommonCodeEnum.ParameterError);
        }
        FileInfoBO fileInfoBO = new FileInfoBO();
        fileInfoBO.setFileKey(fileDTO.getFileKey());
        fileInfoBO.setFileType(fileDTO.getFileType());
        feignFileService.delete(fileInfoBO);
        return Result.success();
    }

    @ApiOperation(value = "修改图片,支持JPG、PNG、JPEG、BMP",
            notes = "type说明：0：上传头像，1：身份认证，3：banner图片，4：logo")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/update")
    public Result<String> update(@ApiParam(value = "0", required = true) Integer fileType,
                         @ApiParam(value = "1234567890", required = true) String fileKey,
                         @RequestParam("file") MultipartFile file) throws IOException {
        if (fileType == null || file == null || StringUtils.isBlank(fileKey)) {
            return Result.error(CommonCodeEnum.ParameterError);
        }
        FileType type = FileType.getByCode(fileType);
        if (type == null) {
            return Result.error(CommonCodeEnum.ParameterError);
        }
        FileInfoBO fileInfoBO = new FileInfoBO();
        fileInfoBO.setFileContentType(file.getContentType());
        fileInfoBO.setFileName(file.getOriginalFilename());
        fileInfoBO.setBytes(file.getBytes());
        fileInfoBO.setFileKey(fileKey);
        fileInfoBO.setFileType(fileType);
        return feignFileService.update(fileInfoBO);
    }
}
