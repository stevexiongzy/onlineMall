package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author xzy
 * @Description: dao service查询接口
 */
public interface IBaseService<T> extends IService<T> {
    /**
     * 单表查询
     */
    List<T> getList(Map<String, Object> params, Class clz);
}
