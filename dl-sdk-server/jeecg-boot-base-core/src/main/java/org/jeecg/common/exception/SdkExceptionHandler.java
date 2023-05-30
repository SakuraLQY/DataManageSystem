package org.jeecg.common.exception;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.SdkResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * sdk异常处理器
 *
 * @Author scott
 * @Date 2019
 */
@RestControllerAdvice(basePackages = {"org.jeecg.modules.users.controller.sdk",
    "org.jeecg.modules.pay.controller.sdk","org.jeecg.modules.game.controller.sdk"})
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SdkExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public SdkResult<?> handleException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return SdkResult.error("input empty");
    }

    @ExceptionHandler(Exception.class)
    public SdkResult<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return SdkResult.error(e.getMessage());
    }

    @ExceptionHandler(IdeaRunTimeException.class)
    public SdkResult<?> handleIdeaRunTimeException(IdeaRunTimeException e) {
        log.error(e.getMessage(), e);
        return SdkResult.error(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 处理Get请求中 @Valid参数错误.
     *
     * @param exception 异常.
     * @return 异常拦截结果.
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public SdkResult<?> bindException(BindException exception) {
        String message = exception.getBindingResult().getAllErrors().stream().map(
            DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error(exception.getMessage(), exception);
        return SdkResult.error(message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常.
     *
     * @param exception 异常.
     * @return 异常拦截结果.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public SdkResult<?> constraintViolationException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream().map(
            ConstraintViolation::getMessage).collect(Collectors.joining(","));
        log.error(exception.getMessage(), exception);
        return SdkResult.error(message);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常.
     *
     * @param exception 异常.
     * @return 异常拦截结果.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public SdkResult<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(","));
        log.error(exception.getMessage(), exception);
        return SdkResult.error(message);
    }

}
