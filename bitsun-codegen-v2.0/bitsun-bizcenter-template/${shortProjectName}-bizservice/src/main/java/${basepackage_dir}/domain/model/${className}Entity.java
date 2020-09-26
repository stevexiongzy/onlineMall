<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign remark=table.remark!>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.domain.model;

import com.bitsun.core.framwork.domain.model.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
<#if table.hasBigDecimal>
import java.math.BigDecimal;
</#if>
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * ${remark!}
 * @Author: ${author!"Felix Woo"}
 * @Email: ${email!"foruforo@msn.com"}
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ${className}Entity  implements Entity<${className}Entity>{


<#list table.columns as column>
<#if    column.columnNameLower != "appId"
        &&column.columnNameLower != "deleted"
        &&column.columnNameLower != "tenantId"
        &&column.columnNameLower != "bg"
        &&column.columnNameLower != "createUser"
        &&column.columnNameLower != "createTime"
        &&column.columnNameLower != "modifyUser"
        &&column.columnNameLower != "modifyTime">
        /**
         * ${column.columnComment!} ${column.sqlTypeName}
         */
        <#if column.sqlTypeName?lower_case == 'jsonb'
        || column.sqlTypeName?lower_case == 'json'>
        private String ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int2'>
        private Short[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int4'>
        private Integer[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_int8'>
        private Long[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == '_varchar'
                || column.sqlTypeName?lower_case == '_text'>
        private String[] ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'timestamp'>
        private LocalDateTime ${column.columnNameLower};
        <#elseif column.sqlTypeName?lower_case == 'date'>
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






