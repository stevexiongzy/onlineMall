package com.xzy.core.common.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 配置属性类
 * @author xzy
 */
@ConfigurationProperties("xzy.core")
@Order(-1)
@Data
@Component
public class CommonConfigProperties {
}
