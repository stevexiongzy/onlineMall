<#assign className=table.className>
<#assign remark=table.remark!>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.dao.${modelName};

import ${basepackage}.po.${modelName}.${className}Po;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

 /**
   * ${remark!}
   * @author: ${author!"Felix Woo"}
   * @email: ${email!"foruforo@msn.com"}
   */
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Po> {
	
}
