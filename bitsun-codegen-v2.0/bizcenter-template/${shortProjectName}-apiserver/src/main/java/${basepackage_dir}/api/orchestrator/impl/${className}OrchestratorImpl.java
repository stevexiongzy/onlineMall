<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.api.orchestrator.impl;

import ${basepackage}.application.${className}Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.bitsun.core.common.exception.AppException;
import com.bitsun.core.common.exception.ErrorCode;
import com.bitsun.core.common.persistence.BasePService;
import com.bitsun.core.common.persistence.IPService;
import com.bitsun.core.common.persistence.Pager;
import com.bitsun.core.common.persistence.QueryParamUtils;
import ${basepackage}.api.orchestrator.${className}Orchestrator;
import ${basepackage}.api.convertor.${className}ReqDtoConvertor;
import ${basepackage}.api.convertor.${className}ResDtoConvertor;
import ${basepackage}.dto.req.${className}ReqDto;
import ${basepackage}.dto.res.${className}ResDto;
import ${basepackage}.infrastructure.persistence.dao.${className}Mapper;
import ${basepackage}.infrastructure.persistence.po.${className}Po;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import java.lang.reflect.Modifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 示例表，应用（业务编排）层实现
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@Slf4j
@Component("${classNameLower}Orchestrator")
public class ${className}OrchestratorImpl implements ${className}Orchestrator {

    @Autowired
    private ${className}Service ${classNameLower}Service;

    @Autowired
    private ${className}ReqDtoConvertor ${classNameLower}ReqDtoConvertor;
    @Autowired
    private ${className}ResDtoConvertor ${classNameLower}ResDtoConvertor;


    @Override
    public ${className}ResDto save(${className}ReqDto reqDto) {
        ${className}Po po = ${classNameLower}ReqDtoConvertor.dto2Po(reqDto);
        ${classNameLower}Service.save(po);
        ${className}ResDto resDto = ${classNameLower}ResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        return ${classNameLower}Service.deleteByIds(ids);
    }

    @Override
    public Pager<${className}ResDto> doPager(Map<String, Object> params) {
        Pager<${className}Po> poPager = ${classNameLower}Service.doPager(params);
        Pager<${className}ResDto> resDtoPager = ${classNameLower}ResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ${className}ResDto selectOne(Long id) {
        ${className}Po po = ${classNameLower}Service.selectOne(id);
        ${className}ResDto resDto = ${classNameLower}ResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public ${className}ResDto selectOne(Map<String, Object> params) {
        ${className}Po po = ${classNameLower}Service.selectOne(params);
        ${className}ResDto resDto = ${classNameLower}ResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        return ${classNameLower}Service.updateProps(id, params);
    }

    @Override
    public boolean updateProps(Long id, ${className}ReqDto reqDto) {
        ${className}Po po = ${classNameLower}ReqDtoConvertor.dto2Po(reqDto);
        return ${classNameLower}Service.updateProps(id,po);
    }


    @Override
    public boolean updateAllProps(Long id, ${className}ReqDto reqDto) {
        ${className}Po po = ${classNameLower}ReqDtoConvertor.dto2Po(reqDto);
        return ${classNameLower}Service.updateAllProps(id, po);
    }

}