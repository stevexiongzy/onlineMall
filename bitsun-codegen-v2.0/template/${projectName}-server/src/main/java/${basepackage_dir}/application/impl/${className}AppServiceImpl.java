<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.application.impl;

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
import ${basepackage}.application.${className}AppService;
import ${basepackage}.domain.assembler.${className}Assembler;
import ${basepackage}.dto.${className}ReqDto;
import ${basepackage}.dto.${className}ResDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if className == "demo">
import ${basepackage}.domain.model.entity.DemoDo;
import org.springframework.beans.factory.annotation.Autowired;
import ${basepackage}.domain.repository.DemoRepository;
import java.math.BigDecimal;
</#if>
/**
 * 示例表，应用（业务编排）层实现
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@Slf4j
@Service("${classNameLower}AppService")
@Transactional(rollbackFor = Exception.class)
public class ${className}AppServiceImpl implements ${className}AppService {
    private IPService<${className}Po> ${classNameLower}PoService;
<#if className == "demo">
    @Autowired
    private DemoRepository demoRepository;
</#if>
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ${className}AppServiceImpl(${className}Mapper ${classNameLower}Mapper) {
        this.${classNameLower}PoService = new BasePService(${classNameLower}Mapper);
    }

    @Override
    public ${className}ResDto save(${className}ReqDto reqDto) {
        ${className}Po po = ${className}Assembler.convertReqDto2Po(reqDto);
        ${classNameLower}PoService.save(po);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
            List<Long> idLongList =
                Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
            return ${classNameLower}PoService.removeByIds(idLongList);
        } catch (Exception e) {
            throw new AppException("参数错误：" + ids, ErrorCode.pc("417"), e);
        }
    }

    @Override
    public Pager<${className}ResDto> doPager(Map<String, Object> params) {
        Pager<${className}Po> poPager = ${classNameLower}PoService.queryPage(params, ${className}Po.class);
        Pager<${className}ResDto> resDtoPager = ${className}Assembler.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ${className}ResDto selectOne(Long id) {
        ${className}Po po = ${classNameLower}PoService.getById(id);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public ${className}ResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(${className}Po::new, params);
        ${className}Po po = ${classNameLower}PoService.getOne(queryWrapper);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<${className}Po> updateWrapper = QueryParamUtils.updateWrapper4Map(${className}Po::new, id, params);
        return ${classNameLower}PoService.update(new ${className}Po(), updateWrapper);
    }

    @Override
    public boolean updateProps(Long id, ${className}ReqDto reqDto) {
        ${className}Po po = ${className}Assembler.convertReqDto2Po(id, reqDto);
        return ${classNameLower}PoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, ${className}ReqDto reqDto) {
        UpdateWrapper<${className}Po> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = ${className}ReqDto.class.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), ${className}ReqDto.class);
                Method getMethod = propertyDescriptor.getReadMethod();
                String fileNameCamel = getMethod.getName().substring(3);
                String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
                updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
            } catch (Exception ex) {
                log.warn("属性不存在get方法："+field.getName(),ex);
            }
        });
        return ${classNameLower}PoService.update(new ${className}Po(), updateWrapper);
    }
<#if className == "demo">

    @Override
    public BigDecimal displayNetProfit(Long id) {
        return DemoDo.builder(id).netProfit();
    }

    @Override
    public void displayRepository(Long id) {
        demoRepository.doComplexTableOperation(id);
    }
</#if>
}