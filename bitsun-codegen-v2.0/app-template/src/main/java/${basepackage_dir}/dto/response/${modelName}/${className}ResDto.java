<#assign className=table.className>
<#assign tableName=table.sqlName>
<#assign remark=table.remark!>
<#assign columns=table.columns>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.dto.response.${modelName};

import ${basepackage}.dto.request.${modelName}.${className}ReqDto;
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
 * @Author: ${author!"Felix Woo"}
 * @Email: ${email!"foruforo@msn.com"}
 */
@Validated
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ${className}ResDto extends ${className}ReqDto implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * bigserial
     */
    @ApiModelProperty(value = "")
    @JsonProperty(index = 0)
    private Long id;

    /**
     * 创建者 varchar
     */
    @ApiModelProperty(value = "创建者")
    @JsonProperty(index = 60)
    private String createUser;
    /**
     * 更新者 varchar
     */
    @ApiModelProperty(value = "更新者")
    @JsonProperty(index = 70)
    private String modifyUser;
    /**
     * 创建时间 timestamp
     */
    @ApiModelProperty(value = "创建时间")
    @JsonProperty(index = 80)
    private LocalDateTime createTime;
    /**
     * 更新时间 timestamp
     */
    @ApiModelProperty(value = "更新时间")
    @JsonProperty(index = 90)
    private LocalDateTime modifyTime;



}






