<#assign className=table.className>
        <#assign tableName=table.sqlName>
        <#assign remark=table.remark!>
        <#assign columns=table.columns>
        <#assign classNameLower=className?uncap_first>
package ${basepackage}.domain.service.impl;

import ${basepackage}.domain.service.I${className}DomainService;

/**
 * @Author: Felix Woo （junfengwstar@gmail.com）
 * @Date: 2019-09-20 19:40
 * @Version: 1.0
 * @Description:
 */
public class ${className}DomainService implements I${className}DomainService{
}