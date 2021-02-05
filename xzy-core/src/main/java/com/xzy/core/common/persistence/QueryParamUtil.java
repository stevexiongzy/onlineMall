package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Function;

/**
 * @author xzy
 * @Description:
 */
@Component
public class QueryParamUtil {
    public static <T> QueryWrapper<T> MapToWrapper(Map<String,Object> params){
        QueryWrapper queryWrapper = Wrappers.query();

        return new QueryWrapper<T>();
    }

}
