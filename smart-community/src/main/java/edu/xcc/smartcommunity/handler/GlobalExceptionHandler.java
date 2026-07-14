package edu.xcc.smartcommunity.handler;

import edu.xcc.smartcommunity.common.Response;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BizException.class)
    public Response handleBizException(Exception e){
        return Response.failed(e.getMessage());
    }
    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception e){
        return Response.failed(e.getMessage());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        StringBuilder builder = new StringBuilder();
// 获取所有的错误消息
        e.getBindingResult().getAllErrors().forEach((error)->{
            builder.append(error.getDefaultMessage());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);
        return Response.failed(e.getMessage());
    }
}
