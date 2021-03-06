<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.api.orchestrator;

import com.bitsun.core.common.persistence.Pager;
import ${basepackage}.dto.req.${className}ReqDto;
import ${basepackage}.dto.res.${className}ResDto;
import java.util.Map;
import javax.validation.Valid;

/**
 * ${remark} service 接口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
public interface ${className}Orchestrator {

    /**
     * 保存一个对象
     * @param reqDto
     * @return
     */
    ${className}ResDto save(${className}ReqDto reqDto);

    /**
     * 删除（支持批量）
     * @param ids
     * @return
     */
    boolean deleteByIds(String ids);

    /**
     * 分页查询
     * @param params
     * @return
     */
    Pager<${className}ResDto> doPager(Map<String, Object> params);

    /**
     * 根据id查询一个对象
     * @param id
     * @return
     */
    ${className}ResDto selectOne(Long id);

    /**
     * 根据其它参数查询一个对象
     * @param params 查询参数
     */
    ${className}ResDto selectOne(Map<String, Object> params);

    /**
     * 更新一个资源对象(更新参数的全部属性)
     * @param id
     * @param params
     * @return
     */
    boolean updateProps(Long id, Map<String, Object> params);

    /**
     * 更新一个资源对象(部分更新)
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateProps(Long id, ${className}ReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换）
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateAllProps(Long id, ${className}ReqDto reqDto);

}