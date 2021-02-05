package com.xzy.core.common.exception;

import com.xzy.core.common.web.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AppException.class)
    public Object handleAppException(AppException e){
        logger.error(e.getMessage(),e);
        return ResultDTO.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e){
        logger.error(e.getMessage(),e);
        return ResultDTO.error("系统服务出错，请联系管理员");
    }

}
