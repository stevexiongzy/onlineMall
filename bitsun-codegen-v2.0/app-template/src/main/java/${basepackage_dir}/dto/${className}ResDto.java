<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign remark=table.remark!>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @Author: ${author!"刘明云"}
 * @Email: ${email!"soul0328@qq.com"}
 */
@Validated
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ${className}ResDto extends ${className}ReqDto implements Serializable{
    private static final long serialVersionUID = 1L;

    <#list table.columns as column>
    <#if  column.columnNameLower == "id"
        ||column.columnNameLower == "createUserId"
        ||column.columnNameLower == "createUserName"
        ||column.columnNameLower == "deleted"
        ||column.columnNameLower == "remarks"
        ||column.columnNameLower == "createTime"
        ||column.columnNameLower == "modifyUserId"
        ||column.columnNameLower == "modifyUserName"
        ||column.columnNameLower == "modifyTime">
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
        <#if column.sqlTypeName?lower_case == 'timestamp'>
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






