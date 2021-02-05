package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 传入列名和参数拼接QueryWapper
 */
interface GetQuery{
    void getQuery(QueryWrapper queryWrapper, String column, Object val);
}
