package ${basepackage}.api.config;

import com.bitsun.core.common.exception.AppExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Felix Woo （foruforo@msn.com）
 * @Date: 2019/5/25 3:46 PM
 * @Version 1.0
 */
@RestControllerAdvice
public class ${projectNameCamel}ExceptionHander extends AppExceptionHandler {
    public ${projectNameCamel}ExceptionHander() {
        super("${projectNameCamel}");
    }
}
