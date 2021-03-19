package com.xzy.core.common.web;

import com.xzy.core.common.constant.ExtraParam;
import com.xzy.core.common.util.ExtraParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取请求时携带头信息的拦截器
 * @author xzy
 */
@Slf4j
@Component
public class ExtraParamInterceptor extends HandlerInterceptorAdapter {

    @Value("${xzy.core.tenantStr:xzy-tenant-id}")
    private String tenantStr;
    @Value("${xzy.core.appIdStr:xzy-app-id}")
    private String appIdStr;
    @Value("${xzy.core.operateIdStr:xzy-operate-id}")
    private String operateIdStr;
    @Value("${xzy.core.operateNameStr:xzy-operate-name}")
    private String operateNameStr;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ExtraParam extraParam = new ExtraParam();

        if(StringUtils.isNumeric(request.getHeader(tenantStr))){
            extraParam.setTenantId(Long.valueOf(request.getHeader(tenantStr)));
        }

        if(StringUtils.isNumeric(request.getHeader(appIdStr))){
            extraParam.setAppId(Long.valueOf(request.getHeader(appIdStr)));
        }

        //TODO:用户信息要从token中解析 并将token传入下一次rpc调用
//        if(StringUtils.isNumeric(request.getHeader(operateIdStr))){
//            extraParam.setOperateId(Long.valueOf(request.getHeader(operateIdStr)));
//        }
//
//        if(StringUtils.isNumeric(request.getHeader(operateNameStr))){
//            extraParam.setOperateName(String.valueOf(request.getHeader(operateNameStr)));
//        }

        ExtraParamUtil.setExtraParam(extraParam);

        return super.preHandle(request, response, handler);
    }
}
