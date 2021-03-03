package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.framework.persistence.BasePo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author xzy
 * @Description:
 */
@Component
public class QueryParamUtil {
    /**
     * 查询条件分隔符
      */
    private static final String QUERY_SPILT_STR = "-";
    /**
     * 查询条件前缀
     */
    private static final String QUERY_PRE_FIX = "qy";
    /**
     * 查询条件属性名称
     */
    private static final String QUERY_FIELD_NAME_STR = "fieldName";
    /**
     * 查询条件操作符名称
     */
    private static final String QUERY_OPERATE_STR = "operate";
    /**
     * 查询条件key中，使用分隔符后共几个字符
     */
    private static final int QUERY_KEY_STR_LEN = 3;
    /**
     * 数据类型为集合时，对应的操作符
     */
    private static final String IN_KEY = "in,not in";
    /**
     * 数据类型为集合时，添加的标识符
     */
    private static final String CollectStrStart = "[";
    private static final String CollectStrEnd = "]";

    /**
     * 传入查询条件map,转化为 queryWrapper, 传参形式为 key: qy-fieldName-eq , value : fieldVal
     * 即传入查询条件 fieldName = fieldVal
     * @param params
     * @param <T> 查询实体类型
     * @return
     */
    public static <T> QueryWrapper<T> MapToWrapper(Map<String,Object> params,Class clz){
        QueryWrapper queryWrapper = Wrappers.query();
        Field[] declaredFields = clz.getDeclaredFields();
        Field[] baseFields = BasePo.class.getDeclaredFields();
        ArrayList<Field> fieldArrayList = Lists.newArrayList(declaredFields);
        fieldArrayList.addAll(Arrays.asList(baseFields));

        params.forEach((k,v) ->{
            if(k.startsWith(QUERY_PRE_FIX)){
                Map<String, String> queryParamFieldMap = getQueryParamField(k);
                if(queryParamFieldMap != null) {
                    String fieldName = queryParamFieldMap.get(QUERY_FIELD_NAME_STR);
                    String operate = queryParamFieldMap.get(QUERY_OPERATE_STR);
                    //校验属性是否存在
                    Field field = validField(fieldName, fieldArrayList);
                    //将传入参数转化为字段对应的类型
                    Object castTypeVal = castType(v, field,operate);
                    QueryConditionEnum queryConditionEnum = QueryConditionEnum.valueOf(operate);
                    queryConditionEnum.getQuery(queryWrapper,camelToUnderline(fieldName),castTypeVal);
                }
            }
        });
        return queryWrapper;
    }

    /**
     * 将传入的参数值，根据对应的查询字段，将参数值的数据类型转化为对应字段的数据类型。
     * @param val 参数值
     * @param field 参数对应的字段
     * @return
     */
    private static Object castType(Object val,Field field,String operate){
        String typeName = field.getType().getTypeName();
        String simpleTypeName = StringUtils.substringAfterLast(typeName,".").toLowerCase();
        String valStr = String.valueOf(val);
        //判断传入参数值是否是集合类型
        if(!StringUtils.isEmpty(operate.toUpperCase()) && IN_KEY.contains(operate)){
            simpleTypeName = simpleTypeName + CollectStrStart+CollectStrEnd;
            //将数据中的[]替换掉
            valStr = valStr.replace(CollectStrStart,"");
            valStr = valStr.replace(CollectStrEnd,"");
        }
        String[] str;
        Object ret ;
        switch (simpleTypeName){
            /** 基本类型转换 ,字符类数据不做处理，可直接使用 */
            case "double": ret = Double.parseDouble(valStr);break;
            case "float": ret = Float.parseFloat(valStr);break;
            case "short": ret = Short.parseShort(valStr);break;
            case "integer":
            case "int": ret = ((Integer)Integer.parseInt(valStr)).intValue();break;
            case "long": ret = Long.parseLong(valStr);break;
            case "boolean": ret = Boolean.parseBoolean(valStr);break;
            /** 集合数据类型 */
            case "double[]":
                str = valStr.split(",");
                List<Double> listDouble = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    listDouble.add(Double.parseDouble(str[i].trim()));
                }
                ret = listDouble;
                break;
            case "float[]":
                str = valStr.split(",");
                List<Float> listFloat = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    listFloat.add(Float.parseFloat(str[i].trim()));
                }
                ret = listFloat;
                break;
            case "short[]":
                str = valStr.split(",");
                List<Short> shortList = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    shortList.add(Short.parseShort(str[i].trim()));
                }
                ret = shortList.isEmpty()?null:shortList;
                break;
            case "integer[]":
            case "int[]":
                str = valStr.split(",");
                List<Integer> listInteger = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    listInteger.add(Integer.parseInt(str[i].trim()));
                }
                ret =  listInteger.isEmpty()?null:listInteger;
                break;
            case "long[]":
                str = valStr.split(",");
                List<Long> listLong = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    listLong.add(Long.parseLong(str[i].trim()));
                }
                ret = listLong.isEmpty()?null:listLong;
                break;
            case "string[]":
                str = valStr.split(",");
                List<String> listString = new ArrayList<>(str.length);
                for (int i = 0; i < str.length; i++) {
                    listString.add(str[i].trim());
                }
                ret = listString.isEmpty()?null:listString;
                break;
            default: ret = val; break;
        }
        return ret;
    }
    /**
     * 校验查询条件属性名称是否在查询对象中，如果不存在，则抛出异常
     * @param fieldName
     * @param fields
     */
    private static Field validField(String fieldName, List<Field> fields){
        return fields.stream().filter(f -> StringUtils.equals(f.getName(), fieldName)).findFirst().orElseThrow(() -> new AppException("查询参数不支持！！"));
    }

    private static Map<String,String> getQueryParamField(String key){
        String[] params = StringUtils.split(key,QUERY_SPILT_STR);

        if(params.length == QUERY_KEY_STR_LEN){
            HashMap<String, String> map = Maps.newHashMap();
            map.put(QUERY_FIELD_NAME_STR,params[1]);
            map.put(QUERY_OPERATE_STR,params[2]);
            return map;
        }
        return null;
    }

    /**
     * 驼峰转下划线
     *
     * @param str 传入需要转换的字符串
     * @return
     */
    private static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()){
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
