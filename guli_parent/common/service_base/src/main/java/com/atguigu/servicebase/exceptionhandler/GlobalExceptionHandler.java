package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //    全局异常处理
//    指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody// 未来返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("系统忙，请稍后重试");
    }

    //    2 特定异常 特殊异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody// 未来返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("除零异常");
    }


    //    3 自定义异常
    @ExceptionHandler(GUliException.class)
    @ResponseBody// 未来返回数据
    public R error(GUliException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}
