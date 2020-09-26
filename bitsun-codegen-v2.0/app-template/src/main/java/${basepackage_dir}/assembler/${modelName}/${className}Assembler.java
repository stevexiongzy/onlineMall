<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.assembler.${modelName};

import com.google.common.collect.Lists;
import com.bitsun.mtof.common.persistence.Pager;
import ${basepackage}.dto.request.${modelName}.${className}ReqDto;
import ${basepackage}.dto.response.${modelName}.${className}ResDto;
import ${basepackage}.po.${modelName}.${className}Po;
import java.util.List;
import javax.validation.Valid;

/**
 * ${remark!}
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
public class ${className}Assembler {

    public static ${className}Po convertReqDto2Po(@Valid ${className}ReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        ${className}Po po = new ${className}Po();
<#list table.columns as column>
    <#if  column.columnNameLower != "id"
        &&column.columnNameLower != "tenantId"
        &&column.columnNameLower != "orgId"
        &&column.columnNameLower != "deleted"
        &&column.columnNameLower != "createUser"
        &&column.columnNameLower != "createTime"
        &&column.columnNameLower != "modifyUser"
        &&column.columnNameLower != "modifyTime">
        po.set${column.columnName}(reqDto.get${column.columnName}());
    </#if>
</#list>
        return po;
    }

    public static ${className}ResDto convertPo2ResDto(${className}Po po) {
        if (po == null) {
            return null;
        }
        ${className}ResDto resDto = new ${className}ResDto();
<#list table.columns as column>
    <#if
        column.columnNameLower != "orgId"
        &&column.columnNameLower != "tenantId"
        &&column.columnNameLower != "deleted">
        resDto.set${column.columnName}(po.get${column.columnName}());
    </#if>
</#list>
        return resDto;
    }

    public static Pager<${className}ResDto> convertPoPager2ResDtoPager(Pager<${className}Po> poPager) {
        if (poPager == null) {
            return null;
        }
        Pager<${className}ResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(convertPoList2ResDtoList(poPager.getList()));
        return resDtoPager;
    }

    private static List<${className}ResDto> convertPoList2ResDtoList(List<${className}Po> poList) {
        if (poList == null || poList.isEmpty()) {
            return null;
        }
        List<${className}ResDto> resDtolist = Lists.newArrayList();
        poList.forEach(po -> resDtolist.add(convertPo2ResDto(po)));
        return resDtolist;
    }

    public static ${className}Po convertReqDto2Po(Long id, @Valid ${className}ReqDto reqDto) {
        ${className}Po po = convertReqDto2Po(reqDto);
        if (po == null) {
            return null;
        }
        po.setId(id);
        return po;
    }
}






