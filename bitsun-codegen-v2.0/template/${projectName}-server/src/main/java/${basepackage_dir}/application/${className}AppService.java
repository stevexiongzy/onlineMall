<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.application;

import com.bitsun.core.framwork.application.IApplicationService;
import com.bitsun.core.common.persistence.Pager;
import ${basepackage}.dto.${className}ReqDto;
import ${basepackage}.dto.${className}ResDto;
import java.util.Map;
import javax.validation.Valid;
<#if className == "demo">
import java.math.BigDecimal;
</#if>
/**
 * ${remark} service 接口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
public interface ${className}AppService extends IApplicationService{

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
<#if className == "demo">

    /**
     * 以下方法是演示用的，演示通过领域对象的能力方法得到净利润
     * @param id
     * @return
     */
    BigDecimal displayNetProfit(Long id);

    /**
     * 演示调用Repository
     */
    void displayRepository(Long id);
</#if>
}