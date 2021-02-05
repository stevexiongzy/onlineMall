package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author xzy
 * @Description: query条件计算枚举
 */

public enum QueryConditionEnum{
    /**
     *  equals
     */
    eq("eq",(query,column,val) ->{
        query.eq(column,val);
    }),
    /**
     * like
     */
    like("like",(query,column,val) ->{
        query.like(column,val);
    });
    private final GetQuery operator;
    String code;

    QueryConditionEnum(String eq, GetQuery operator) {
        this.code = eq;
        this.operator = operator;
    }

    public QueryWrapper getQuery(QueryWrapper queryWrapper,String column,Object val){
        this.operator.getQuery(queryWrapper, column, val);
        return queryWrapper;
    }

}
