package com.upc.srp.utils;

import com.upc.srp.dto.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by zhoutao
 * @description 全局异常处理类
 * @date 2019/12/29 21:01
 */
@ControllerAdvice
public class GobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response errorHandler(Exception ex) {
        return Response.getBuilder().setCode(500).setMessage("error").setData(ex.getMessage()).build();
    }
}
