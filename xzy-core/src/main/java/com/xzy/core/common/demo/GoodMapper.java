package com.xzy.core.common.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    IPage<Good> selectAllById(Page page);
    Good selectAllByIdAfter();
}
