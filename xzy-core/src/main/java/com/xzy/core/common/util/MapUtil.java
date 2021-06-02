package com.xzy.core.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建Map参数工具类
 * @author xzy
 */
public class MapUtil extends HashMap<String,Object> {

    @Override
    public MapUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public MapUtil putMap(Map<String,Object> params) {
        super.putAll(params);
        return this;
    }
}
