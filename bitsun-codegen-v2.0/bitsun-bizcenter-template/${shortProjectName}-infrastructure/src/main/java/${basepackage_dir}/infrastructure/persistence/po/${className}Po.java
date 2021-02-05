<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bitsun.core.common.mybatis.handler.JsonTypeHandler;
import com.bitsun.core.framwork.infrastructure.persistence.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
<#if table.hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;
import java.time.*;

/**
 * ${table.remark!}
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "${tableName}", resultMap = "${className?uncap_first}Map")
public class ${className}Po extends BasePo<${className}Po> implements Serializable{
    private static final long serialVersionUID = 1L;

    public ${className}Po(){

    }
    public ${className}Po(Long id){
        this.id = id;
    }
    <#list table.columns as column>
    <#if  column.columnNameLower != "id"
        &&column.columnNameLower != "appId"
        &&column.columnNameLower != "tenantId"
        &&column.columnNameLower != "bg"
        &&column.columnNameLower != "deleted"
        &&column.columnNameLower != "createUserId"
        &&column.columnNameLower != "createUserName"
        &&column.columnNameLower != "createTime"
        &&column.columnNameLower != "modifyUserId"
        &&column.columnNameLower != "modifyUserName"
        &&column.columnNameLower != "modifyTime">

    /**
     * ${column.columnComment!} ${column.sqlTypeName}
     */
        <#if column.sqlTypeName?lower_case == 'jsonb' >
    @TableField(value="${column.columnName",typeHandler = JsonTypeHandler.class)
    private String ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'json' >
    @TableField(value="${column.columnName",typeHandler = JsonTypeHandler.class)
    private String ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int2'>
    @TableField(value="${column.columnName",el = "${column.columnNameLower}, typeHandler=com.bitsun.core.common.mybatis.handler.ArrayTypeHandler")
    private Short[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int4'>
    @TableField(value="${column.columnName",el = "${column.columnNameLower}, typeHandler=com.bitsun.core.common.mybatis.handler.ArrayTypeHandler")
    private Integer[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int8'>
    @TableField(value="${column.columnName",el = "${column.columnNameLower}, typeHandler=com.bitsun.core.common.mybatis.handler.ArrayTypeHandler")
    private Long[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_varchar'
                || column.sqlTypeName?lower_case == '_text'>
    @TableField(value="${column.columnName",el = "${column.columnNameLower}, typeHandler=com.bitsun.core.common.mybatis.handler.ArrayTypeHandler")
    private String[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'timestamp'>
    private LocalDateTime ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case?lower_case == 'date'>
    private LocalDate ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'time'>
    private LocalTime ${column.columnNameLower};
        <#elseif column.javaType == 'java.math.BigDecimal'>
    private BigDecimal ${column.columnNameLower};
        <#else>
    private ${column.javaType} ${column.columnNameLower};
        </#if>
    </#if>
</#list>

}






