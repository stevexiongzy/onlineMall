package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;

/**
 * @author xzy
 * @Description: query条件计算枚举
 */

public enum QueryConditionEnum{
    /**
     *  equals 相等
     */
    eq("eq",(query,column,val) ->{
        query.eq(column,val);
    }),
    /**
     * like 模糊匹配
     */
    like("like",(query,column,val) ->{
        query.like(column,val);
    }),
    /**
     * ne 不等于 <>
     */
    ne("ne",(query,column,val) ->{
        query.ne(column,val);
    }),
    /**
     * gt 大于 >
     */
    gt("gt",(query,column,val) ->{
        query.gt(column,val);
    }),
    /**
     * in 字段 IN (value.get(0), value.get(1), ...)
     */
    in("in",(query,column,val) ->{
        if(val instanceof ArrayList){
            query.in(column,(ArrayList)val);
        }else {
            query.in(column,val);
        }
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
