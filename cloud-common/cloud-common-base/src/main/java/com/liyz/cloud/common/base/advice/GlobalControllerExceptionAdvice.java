package com.liyz.cloud.common.base.advice;

import com.liyz.cloud.common.exception.CommonExceptionCodeEnum;
import com.liyz.cloud.common.exception.RemoteServiceException;
import com.liyz.cloud.common.feign.result.Result;
import com.liyz.cloud.common.util.constant.CommonConstant;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Desc:全局异常处理器
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/9 15:18
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public Result<String> exception(Exception exception) {
        if (exception.getCause() instanceof RemoteServiceException) {
            return remoteServiceException((RemoteServiceException) exception.getCause());
        }
        log.error("未知异常", exception);
        return Result.error(CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
    }

    @ExceptionHandler({RemoteServiceException.class})
    public Result<String> remoteServiceException(RemoteServiceException exception) {
        return Result.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler({BindException.class})
    public Result<String> bindException(BindException exception) {
        if (Objects.nonNull(exception) && exception.hasErrors()) {
            List<ObjectError> errors = exception.getAllErrors();
            for (ObjectError error : errors) {
                if (error.contains(TypeMismatchException.class) && error instanceof FieldError fieldError) {
                    return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), fieldError.getField() + "类型转化失败");
                }
                return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), error.getDefaultMessage());
            }
        }
        return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED);
    }

    @ExceptionHandler({ValidationException.class})
    public Result<String> validationException(ValidationException exception) {
        String[] message = exception.getMessage().split(CommonConstant.DEFAULT_JOINER);
        if (message.length >= 2) {
            return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), message[1].trim());
        }
        return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public Result<String> constraintViolationException(ConstraintViolationException exception) {
        if (CollectionUtils.isEmpty(exception.getConstraintViolations())) {
            return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED);
        }
        Optional<ConstraintViolation<?>> optional = exception.getConstraintViolations().stream().findFirst();
        return optional
                .<Result<String>>map(cv -> Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), cv.getMessageTemplate()))
                .orElseGet(() -> Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED));
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public Result<String> noResourceFoundException(NoResourceFoundException exception) {
        return Result.error(CommonExceptionCodeEnum.NOT_FOUND);
    }
}
