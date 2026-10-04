package com.atguigu.order.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : LiuJie
 * @description : 全局异常处理
 * @date : 2026/9/10
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler(Throwable.class)
    public String error(){
        return "";
    }
}
