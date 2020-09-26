<#assign sqlName=table.sqlName>
        <#assign className=table.className>
        <#assign classNameLower=table.classNameLower>
        <#assign remark=table.remark!>
package ${basepackage}.convertor;

import ${basepackage}.domain.model.${className}Entity;
import com.google.common.collect.Lists;
import com.bitsun.core.common.persistence.Pager;
import ${basepackage}.dto.req.${className}ReqDto;
import ${basepackage}.dto.res.${className}ResDto;
import ${basepackage}.infrastructure.persistence.po.${className}Po;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.bitsun.core.common.convertor.IConvertor;
/**
 * ${remark!}
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */

@Mapper(componentModel="spring")
public abstract class ${className}ResDtoConvertor implements IConvertor<${className}ResDto,${className}Entity,${className}Po> {

    public  Pager<${className}ResDto> convertPoPager2ResDtoPager(Pager<${className}Po> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<${className}ResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








