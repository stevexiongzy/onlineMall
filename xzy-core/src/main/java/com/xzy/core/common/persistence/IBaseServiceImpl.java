package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @author xzy
 * @Description: dao service查询接口
 */
public class IBaseServiceImpl<M extends BaseMapper<P>,P> extends ServiceImpl<M,P> implements IBaseService<P> {
    public IBaseServiceImpl(M baseMapper){
        this.baseMapper = baseMapper;
    }

    @Override
    public List<P> getList(Map<String, Object> params, Class clz) {
        return null;
    }
}
