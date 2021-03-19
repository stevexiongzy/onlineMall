package com.xzy.core.common.exception;

import com.xzy.core.common.web.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author xzy
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class AppExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AppException.class)
    public Object handleAppException(AppException e){
        //自定义异常 处理器中不打印日志 webLog会进行打印
        return ResultDTO.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e){
        logger.error(e.getMessage());
        return ResultDTO.error("系统服务出错，请联系管理员");
    }

}
