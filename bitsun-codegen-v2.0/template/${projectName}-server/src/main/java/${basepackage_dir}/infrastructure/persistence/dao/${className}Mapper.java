<#assign className=table.className>
<#assign remark=table.remark!>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.infrastructure.persistence.dao;

import ${basepackage}.infrastructure.persistence.po.${className}Po;
import com.bitsun.core.framwork.infrastructure.persistence.dao.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

 /**
   * ${remark!}
   * @author: ${author!"Felix Woo"}
   * @email: ${email!"foruforo@msn.com"}
   */
@Mapper
public interface ${className}Mapper extends BaseExtMapper<${className}Po> {
	
}
