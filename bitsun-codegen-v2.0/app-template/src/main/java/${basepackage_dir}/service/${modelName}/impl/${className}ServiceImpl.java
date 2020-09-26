<#assign sqlName=table.sqlName>
<#assign className=table.className>
<#assign classNameLower=table.classNameLower>
<#assign remark=table.remark!>
package ${basepackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.bitsun.mtof.common.exception.AppException;
import com.bitsun.mtof.common.exception.ErrorCode;
import com.bitsun.mtof.common.persistence.BaseServiceImpl;
import com.bitsun.mtof.common.persistence.IPService;
import com.bitsun.mtof.common.persistence.Pager;
import com.bitsun.mtof.common.persistence.QueryParamUtils;
import ${basepackage}.service.${modelName}.${className}Service;
import ${basepackage}.dto.request.${modelName}.${className}ReqDto;
import ${basepackage}.dto.response.${modelName}.${className}ResDto;
import ${basepackage}.dao.${modelName}.${className}Mapper;
import ${basepackage}.po.${modelName}.${className}Po;
import ${basepackage}.assembler.${modelName}.${className}Assembler;
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
/**
 * 示例表，应用（业务编排）层实现
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@Slf4j
@Service("${classNameLower}Service")
@Transactional(rollbackFor = Exception.class)
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Mapper, ${className}Po> implements ${className}Service {

    @Override
    public ${className}ResDto save${className}(${className}ReqDto reqDto) {
        ${className}Po po = ${className}Assembler.convertReqDto2Po(reqDto);
        super.save(po);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public boolean delete${className}ByIds(String ids) {
        try {
            List<Long> idLongList =
                Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
            return super.removeByIds(idLongList);
        } catch (Exception e) {
            throw new AppException("参数错误：" + ids, ErrorCode.pc("417"), e);
        }
    }

    @Override
    public Pager<${className}ResDto> get${className}Pager(Map<String, Object> params) {
        Pager<${className}Po> poPager = super.queryPage(params, ${className}Po.class);
        Pager<${className}ResDto> resDtoPager = ${className}Assembler.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ${className}ResDto selectOne${className}(Long id) {
        ${className}Po po = super.getById(id);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public ${className}ResDto selectOne${className}(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(${className}Po::new, params);
        ${className}Po po = super.getOne(queryWrapper);
        ${className}ResDto resDto = ${className}Assembler.convertPo2ResDto(po);
        return resDto;
    }

    @Override
    public boolean update${className}Props(Long id, Map<String, Object> params) {
        UpdateWrapper<${className}Po> updateWrapper = QueryParamUtils.updateWrapper4Map(${className}Po::new, id, params);
        return super.update(new ${className}Po(), updateWrapper);
    }

    @Override
    public boolean update${className}Props(Long id, ${className}ReqDto reqDto) {
        ${className}Po po = ${className}Assembler.convertReqDto2Po(id, reqDto);
        return super.updateById(po);
    }


    @Override
    public boolean update${className}AllProps(Long id, ${className}ReqDto reqDto) {
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
        return super.update(new ${className}Po(), updateWrapper);
    }
}