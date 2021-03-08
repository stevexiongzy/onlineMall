package com.xzy.core.common.util;

import com.xzy.core.common.constant.ExtraParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

/**
 * 存储请求header信息的工具类，使用threadlocal实现
 * @author xzy
 */
@Slf4j
public class ExtraParamUtil {
    private final static ThreadLocal<ExtraParam> extraParamLocal = new InheritableThreadLocal<>();

    public static void setExtraParam(ExtraParam extraParam){
        extraParamLocal.set(extraParam);
    }

    public static ExtraParam getExtraParam(){
        ExtraParam extraParam = extraParamLocal.get();
        if (!Optional.ofNullable(extraParam).isPresent()){
            //没获取到 新创建一个空的放入当前线程中
            extraParam = new ExtraParam();
            extraParamLocal.set(extraParam);
        }
        return extraParam;
    }

    public static void destory(){
        extraParamLocal.remove();
    }
}
