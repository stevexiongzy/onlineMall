package com.xzy.core.common.persistence.interceptor;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xzy.core.common.util.ExtraParamUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 多租户sql注入器
 * @author xzy
 */
@Component
public class TenantInterceptor implements TenantLineHandler {

    /**
     * 需要过滤的表，使用逗号分隔
     */
    private String ignoreTables;
    /**
     * 数据库租户列 列名 默认tenant_id
     */
    private String tenantColumnName;

    public TenantInterceptor(){

    }

    public TenantInterceptor(String ignoreTables,String tenantColumnName){
        this.tenantColumnName = tenantColumnName;
        this.ignoreTables = ignoreTables;
    }

    @Override
    public Expression getTenantId() {
        //获取租户id 为空时使用默认租户id
        Long tenantId = ExtraParamUtil.getExtraParam().getTenantId();
        return new LongValue(Optional.ofNullable(tenantId).orElseGet(() -> 1L));
    }

    @Override
    public String getTenantIdColumn() {
        return tenantColumnName;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        //过滤表
        if(!StringUtils.isEmpty(ignoreTables) && ignoreTables.contains(tableName)){
            return true;
        }
        return false;
    }
}
