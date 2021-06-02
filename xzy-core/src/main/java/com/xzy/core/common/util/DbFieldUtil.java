package com.xzy.core.common.util;

/**
 * 数据库字段工具类
 * @author xzy
 */
public class DbFieldUtil {

    /**
     * 驼峰转下划线
     *
     * @param str 传入需要转换的字符串
     * @return String下划线字段
     */
    public static String camelToUnderline(String str) {

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
