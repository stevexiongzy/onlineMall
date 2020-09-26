<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.controller.${modelName}.impl;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.bitsun.mtof.common.persistence.Pager;
import com.bitsun.mtof.common.web.ResultDTO;
import ${basepackage}.service.${modelName}.${className}Service;
import ${basepackage}.dto.request.${modelName}.${className}ReqDto;
import ${basepackage}.dto.response.${modelName}.${className}ResDto;
import ${basepackage}.controller.${modelName}.${className}Api;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ${remark!}
 * @Author: ${author!"Felix Woo"}
 * @Email: ${email!"foruforo@msn.com"}
 */
@Slf4j
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/${classNameLower}")
public class ${className}ApiController implements ${className}Api {

    @Autowired
    private ${className}Service ${classNameLower}Service;

    @Override
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:save")
    public ResultDTO<${className}ResDto> postInsert${className}(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${className}ResDto resDto = ${classNameLower}Service.save${className}(reqDto);
        return ResultDTO.ok(resDto);
    }

    @Override
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:delete")
    public ResultDTO<Void> delete${className}(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = ${classNameLower}Service.delete${className}ByIds(ids);
        return ResultDTO.ok();
    }

    @Override
    @GetMapping(produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:list")
    public ResultDTO<Pager<${className}ResDto>> get${className}List(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<${className}ResDto> resDtoPager = ${classNameLower}Service.get${className}Pager(params);
        return ResultDTO.ok(resDtoPager);
    }

    @Override
    @GetMapping(value = "/{id}", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:list")
    public ResultDTO<${className}ResDto> getOne${className}(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        ${className}ResDto resDto = ${classNameLower}Service.selectOne${className}(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @GetMapping(value = "/one", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:list")
    public ResultDTO<${className}ResDto> getOne${className}(@ApiIgnore @RequestParam Map<String, Object> params) {
        ${className}ResDto resDto = ${classNameLower}Service.selectOne${className}(params);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:list")
    public ResultDTO<${className}ResDto> patchUpdate${className}(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        ${classNameLower}Service.update${className}Props(id, params);
        ${className}ResDto resDto = ${classNameLower}Service.selectOne${className}(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:save")
    public ResultDTO<${className}ResDto> patchUpdate${className}(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${classNameLower}Service.update${className}Props(id, reqDto);
        ${className}ResDto resDto = ${classNameLower}Service.selectOne${className}(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PutMapping(value = "/{id}", produces = {"application/json"})
    @RequiresPermissions("${classNameLower}:save")
    public ResultDTO<${className}ResDto> putUpdate${className}(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${classNameLower}Service.update${className}AllProps(id, reqDto);
        ${className}ResDto resDto = ${classNameLower}Service.selectOne${className}(id);
        return ResultDTO.ok(resDto);
    }
}
