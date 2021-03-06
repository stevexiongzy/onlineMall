<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign remark=table.remark!>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.dto.res;

import com.bitsun.core.framwork.api.dto.IResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
<#assign dtflag=0>
    <#assign dflag=0>
    <#assign tflag=0>
    <#assign jsonflag=0>
    <#assign statusflag=0>
    <#list table.columns as column>
    <#if column.columnNameLower != "createTime"
    &&column.columnNameLower != "modifyTime">
    <#if column.sqlTypeName?lower_case == 'timestamp' && dtflag == 0>
    <#assign dtflag = dtflag+1>
import java.time.LocalDateTime;
    <#elseif column.sqlTypeName?lower_case == 'date' && dflag == 0>
        <#assign dflag = dflag+1>
import java.time.LocalDate;
    <#elseif column.sqlTypeName?lower_case == 'time' && tflag == 0>
        <#assign tflag = tflag+1>
import java.time.LocalTime;
    <#elseif (column.sqlTypeName?lower_case == 'jsonb'
        || column.sqlTypeName?lower_case == 'json') && jsonflag == 0>
        <#assign jsonflag = jsonflag+1>
import com.bitsun.core.common.validator.JsonValid;
    <#elseif column.columnNameLower == "status" && statusflag == 0>
        <#assign statusflag = statusflag+1>
import com.bitsun.core.common.validator.In;
    <#elseif dtflag == 1 && dflag == 1 && tflag == 1 && jsonflag == 1 && statusflag == 1>
        <#break>
        </#if>
        </#if>
        </#list>
        <#if table.hasBigDecimal>
import java.math.BigDecimal;
</#if>
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * ${remark!}
 * @Author: ${author!"Felix Woo"}
 * @Email: ${email!"foruforo@msn.com"}
 */
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class ${className}ResDto  implements IResponseDto{
    private static final long serialVersionUID = 1L;


<#list table.columns as column>
<#if
    column.columnNameLower != "appId"
    &&column.columnNameLower != "tenantId"
    &&column.columnNameLower != "deleted"
    >
/**
 * ${column.columnComment!} ${column.sqlTypeName}
 */
<#if column.sqlTypeName?lower_case == 'date'>
@ApiModelProperty(value = "${column.columnComment!}", example = "yyyy-MM-dd")
<#elseif column.sqlTypeName?lower_case == 'time'>
@ApiModelProperty(value = "${column.columnComment!}", example = "HH:mm:ss", dataType = "java.lang.String")
<#else>
@ApiModelProperty(value = "${column.columnComment!}")
</#if>
@JsonProperty(index = ${column_index * 10})
<#if column.sqlTypeName?lower_case == 'jsonb'
    || column.sqlTypeName?lower_case == 'json'>
@JsonValid
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






