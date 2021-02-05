package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author xzy
 * @Description: dao service查询接口
 */
public class IBaseServiceImpl<M extends BaseMapper<P>,P> extends ServiceImpl<M,P> implements IBaseService<P> {
    public IBaseServiceImpl(M baseMapper){
        this.baseMapper = baseMapper;
    }
}
