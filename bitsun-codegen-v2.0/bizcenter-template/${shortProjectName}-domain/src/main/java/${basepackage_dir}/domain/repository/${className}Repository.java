<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.domain.repository;

import com.bitsun.core.framwork.domain.repository.IRepository;
import com.bitsun.core.common.persistence.Pager;
import ${basepackage}.domain.model.${className}Entity;
import java.util.Map;
import javax.validation.Valid;

/**
 * ${remark} service 接口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
public interface ${className}Repository<${className}Entity> extends IRepository{


}