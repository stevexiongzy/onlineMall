<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.interfaces;

import com.bitsun.core.common.persistence.Pager;
import com.bitsun.core.common.web.ResultDTO;
import ${basepackage}.dto.${className}ReqDto;
import ${basepackage}.dto.${className}ResDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Map;

/**
 * ${remark!}
 * @Author: ${author!"Felix Woo"}
 * @Email: ${email!"foruforo@msn.com"}
 */
@Api(value = "${classNameLower}", description = "${remark!}接口", tags = {"${classNameLower}"})
public interface ${className}Api {

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"${classNameLower}"}, nickname = "doPostInsert")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    ResultDTO<${className}ResDto> doPostInsert(${className}ReqDto reqDto);

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"${classNameLower}",}, nickname = "doDelete")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<Void> doDelete(String ids);

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
        "${classNameLower}",}, nickname = "doGetList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataType = "long"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataType = "long"),
        @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query"),
<#list table.columns as column>
    <#if  column.columnNameLower != "id"
    &&column.columnNameLower != "remarks"
    &&column.columnNameLower != "appId"
    &&column.columnNameLower != "deleted">
        @ApiImplicitParam(name = "qp-${column.columnNameLower}-eq", value = "${column.columnComment!}", paramType = "query"),
    </#if>
</#list>
        @ApiImplicitParam(name = "qp-remark-eq", value = "备注", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<Pager<${className}ResDto>> doGetList(Map<String, Object> params);

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"${classNameLower}"}, nickname = "doGetOne")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> doGetOne(Long id);

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"${classNameLower}"}, nickname = "doGetOneByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
<#list table.columns as column>
    <#if  column.columnNameLower != "id"
    &&column.columnNameLower != "remarks"
    &&column.columnNameLower != "appId"
    &&column.columnNameLower != "deleted"
    &&column.columnNameLower != "createUser"
    &&column.columnNameLower != "createTime"
    &&column.columnNameLower != "modifyUser"
    &&column.columnNameLower != "modifyTime">
        @ApiImplicitParam(name = "${column.columnNameLower}", value = "${column.columnComment!}", paramType = "query"),
    </#if>
</#list>
        @ApiImplicitParam(name = "remark", value = "备注", paramType = "query")
    })
    ResultDTO<${className}ResDto> doGetOne(Map<String, Object> params);

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"${classNameLower}"}, nickname = "doPatchUpdateByMap")
    @ApiImplicitParams({
        <#list table.columns as column>
        <#if  column.columnNameLower != "id"
        &&column.columnNameLower != "remarks"
        &&column.columnNameLower != "appId"
        &&column.columnNameLower != "deleted"
        &&column.columnNameLower != "createUser"
        &&column.columnNameLower != "createTime"
        &&column.columnNameLower != "modifyUser"
        &&column.columnNameLower != "modifyTime">
        @ApiImplicitParam(name = "${column.columnNameLower}", value = "${column.columnComment!}", paramType = "query"),
        </#if>
        </#list>
        @ApiImplicitParam(name = "remark", value = "备注", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> doPatchUpdate(Long id, Map<String, Object> params);
    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"${classNameLower}"}, nickname = "doPatchUpdate")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> doPatchUpdate(Long id, ${className}ReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"${classNameLower}"}, nickname = "doPutUpdate")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> doPutUpdate(Long id, ${className}ReqDto reqDto);

}
