package com.xzy.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于存储header头信息的实体类
 * @author xzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraParam {
    /**多租户隔离**/
    Long tenantId;
    /**应用信息**/
    Long appId;
}
