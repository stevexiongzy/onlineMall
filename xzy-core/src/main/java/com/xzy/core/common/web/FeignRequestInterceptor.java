package com.xzy.core.common.web;

import com.xzy.core.common.constant.ExtraParam;
import com.xzy.core.common.util.ExtraParamUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * feign请求调用其他服务时 把当前服务的header信息放入requestTemplate中
 * @author xzy
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Value("${xzy.core.tenantStr:xzy-tenant-id}")
    private String tenantStr;
    @Value("${xzy.core.appIdStr:xzy-app-id}")
    private String appIdStr;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ExtraParam extraParam = ExtraParamUtil.getExtraParam();

        Optional.ofNullable(extraParam.getTenantId()).ifPresent(tenantId -> requestTemplate.header(tenantStr,tenantId.toString()));

        Optional.ofNullable(extraParam.getAppId()).ifPresent(appId -> requestTemplate.header(appIdStr,appId.toString()));

    }
}
