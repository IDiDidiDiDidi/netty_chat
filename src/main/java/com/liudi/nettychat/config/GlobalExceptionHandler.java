package com.liudi.nettychat.config;

import com.liudi.nettychat.response.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public Message exceptionHandler(HttpServletRequest request, Exception e) {
        //数据校验异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = exception.getBindingResult();
            StringBuilder sb = new StringBuilder();
            //取第一条展示给前端
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (fieldErrors.size() > 0) {
                log.warn("校验失败=>{}:{}", fieldErrors.get(0).getField(), fieldErrors.get(0).getDefaultMessage());
                sb.append("校验失败:").append(fieldErrors.get(0).getDefaultMessage());
            }
            return Message.fail(sb.toString());
        }
        //自定义异常==>是需要明确提示给用户的
        else if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            log.warn("自定义异常:{}，异常类型：{}", e.getMessage(), e.getClass());
            return Message.fail(exception.getMessage());
        }
        //其他异常
        else {
            // 其余异常简单返回为服务器异常
            log.error("全局异常拦截:{0}", e);
            return Message.fail("连接超时，请稍后重试！");
        }
    }
}
