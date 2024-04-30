package cn.wangjiahang.whimsy.shorturl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public Result<?> validException(BindException e) {
        log.error("参数验证错误", e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);

        return Result.error(result.get("errorMsg").toString(), result.get("errorList"));
    }


    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     *
     * @return boResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> validException(MethodArgumentNotValidException e) {
        log.error("参数验证错误", e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return Result.error(result.get("errorMsg").toString(), result.get("errorList"));
    }

    /**
     * 单个参数验证.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> validExceptionHandler(ConstraintViolationException ex) {
        log.error("参数验证错误", ex);

        return Result.error(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("")));
    }

    /**
     * 获取校验错误信息
     *
     * @param fieldErrors 字段错误值
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> errorList = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer("校验错误：");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        final String msg = StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "服务器内部错误";
        log.error(msg, e);

        return Result.error(msg);
    }
}
