<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.interfaces.rest;

import com.bitsun.core.common.persistence.Pager;
import com.bitsun.core.common.web.ResultDTO;
import ${basepackage}.application.${className}AppService;
import ${basepackage}.dto.${className}ReqDto;
import ${basepackage}.dto.${className}ResDto;
import ${basepackage}.interfaces.${className}Api;
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
    private ${className}AppService ${classNameLower}AppService;

    @Override
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<${className}ResDto> doPostInsert(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${className}ResDto resDto = ${classNameLower}AppService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    @Override
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> doDelete(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = ${classNameLower}AppService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    @Override
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<${className}ResDto>> doGetList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<${className}ResDto> resDtoPager = ${classNameLower}AppService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    @Override
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<${className}ResDto> doGetOne(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        ${className}ResDto resDto = ${classNameLower}AppService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<${className}ResDto> doGetOne(@ApiIgnore @RequestParam Map<String, Object> params) {
        ${className}ResDto resDto = ${classNameLower}AppService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<${className}ResDto> doPatchUpdate(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        ${classNameLower}AppService.updateProps(id, params);
        ${className}ResDto resDto = ${classNameLower}AppService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<${className}ResDto> doPatchUpdate(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${classNameLower}AppService.updateProps(id, reqDto);
        ${className}ResDto resDto = ${classNameLower}AppService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @Override
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<${className}ResDto> doPutUpdate(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody ${className}ReqDto reqDto) {
        ${classNameLower}AppService.updateAllProps(id, reqDto);
        ${className}ResDto resDto = ${classNameLower}AppService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
