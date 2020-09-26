<#assign className=table.className>
        <#assign tableName=table.sqlName>
        <#assign remark=table.remark!>
        <#assign columns=table.columns>
        <#assign classNameLower=className?uncap_first>
package ${basepackage}.domain.service.impl;

import ${basepackage}.convertor.${className}ReqDtoConvertor;
import ${basepackage}.convertor.${className}ResDtoConvertor;
import com.bitsun.core.common.persistence.BasePService;
import com.bitsun.core.common.persistence.IPService;
import ${basepackage}.infrastructure.persistence.dao.${className}Mapper;
import ${basepackage}.infrastructure.persistence.po.${className}Po;
import ${basepackage}.domain.service.${className}DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Felix Woo （junfengwstar@gmail.com）
 * @Date: 2019-09-20 19:40
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service("${classNameLower}DomainService")
public class ${className}DomainServiceImpl implements ${className}DomainService{

    @Autowired
    private ${className}ReqDtoConvertor ${classNameLower}ReqDtoConvertor;

    @Autowired
    private ${className}ResDtoConvertor ${classNameLower}ResDtoConvertor;

    private IPService<${className}Po> ${classNameLower}PoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ${className}DomainServiceImpl(${className}Mapper ${classNameLower}Mapper) {
        this.${classNameLower}PoService = new BasePService(${classNameLower}Mapper);
    }
}