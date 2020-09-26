<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.controller.${modelName};

import com.bitsun.mtof.common.persistence.Pager;
import com.bitsun.mtof.common.web.ResultDTO;
import ${basepackage}.dto.request.${modelName}.${className}ReqDto;
import ${basepackage}.dto.response.${modelName}.${className}ResDto;
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
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"${classNameLower}"}, nickname = "postInsert${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    ResultDTO<${className}ResDto> postInsert${className}(${className}ReqDto reqDto);

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"${classNameLower}",}, nickname = "delete${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<Void> delete${className}(String ids);

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
        "${classNameLower}",}, nickname = "get${className}List")
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
        @ApiImplicitParam(name = "qp-remarks-eq", value = "备注", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<Pager<${className}ResDto>> get${className}List(Map<String, Object> params);

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"${classNameLower}"}, nickname = "getOne${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> getOne${className}(Long id);

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"${classNameLower}"}, nickname = "getOne${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
<#list table.columns as column>
    <#if  column.columnNameLower != "id"
    &&column.columnNameLower != "remarks"
&&column.columnNameLower != "appId"
&&column.columnNameLower != "deleted"
&&column.columnNameLower != "createUserName"
&&column.columnNameLower != "createUserId"
&&column.columnNameLower != "createTime"
&&column.columnNameLower != "modifyUserName"
&&column.columnNameLower != "modifyUserId"
&&column.columnNameLower != "modifyTime">
        @ApiImplicitParam(name = "${column.columnNameLower}", value = "${column.columnComment!}", paramType = "query"),
    </#if>
</#list>
        @ApiImplicitParam(name = "remarks", value = "备注", paramType = "query")
    })
    ResultDTO<${className}ResDto> getOne${className}(Map<String, Object> params);

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"${classNameLower}"}, nickname = "patchUpdate${className}")
    @ApiImplicitParams({
        <#list table.columns as column>
        <#if  column.columnNameLower != "id"
        &&column.columnNameLower != "remarks"
&&column.columnNameLower != "appId"
&&column.columnNameLower != "deleted"
&&column.columnNameLower != "createUserName"
&&column.columnNameLower != "createUserId"
&&column.columnNameLower != "createTime"
&&column.columnNameLower != "modifyUserName"
&&column.columnNameLower != "modifyUserId"
&&column.columnNameLower != "modifyTime">
        @ApiImplicitParam(name = "${column.columnNameLower}", value = "${column.columnComment!}", paramType = "query"),
        </#if>
        </#list>
        @ApiImplicitParam(name = "remarks", value = "备注", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> patchUpdate${className}(Long id, Map<String, Object> params);
    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"${classNameLower}"}, nickname = "patchUpdate${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> patchUpdate${className}(Long id, ${className}ReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"${classNameLower}"}, nickname = "putUpdate${className}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    ResultDTO<${className}ResDto> putUpdate${className}(Long id, ${className}ReqDto reqDto);

}
