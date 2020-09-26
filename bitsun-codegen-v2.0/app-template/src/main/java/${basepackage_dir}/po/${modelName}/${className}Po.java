<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.po.${modelName};

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ${basepackage}.po.BasePo;
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
@KeySequence("${tableName}_id_seq")
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
        &&column.columnNameLower != "remarks"
    &&column.columnNameLower != "orgId"
    &&column.columnNameLower != "tenantId"
        &&column.columnNameLower != "deleted"
        &&column.columnNameLower != "createUser"
        &&column.columnNameLower != "createTime"
        &&column.columnNameLower != "modifyUser"
        &&column.columnNameLower != "modifyTime">

    /**
     * ${column.columnComment!} ${column.sqlTypeName}
     */
        <#if column.sqlTypeName?lower_case == 'jsonb' >
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.JsonbTypeHandler")
    private String ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'json' >
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.JsonTypeHandler")
    private String ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int2'>
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.ArrayTypeHandler")
    private Short[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int4'>
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.ArrayTypeHandler")
    private Long[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int8'>
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.ArrayTypeHandler")
    private Long[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_varchar'
                || column.sqlTypeName?lower_case == '_text'>
    @TableField(el = "${column.columnNameLower}, typeHandler=com.bitsun.mtof.common.mybatis.handler.ArrayTypeHandler")
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






