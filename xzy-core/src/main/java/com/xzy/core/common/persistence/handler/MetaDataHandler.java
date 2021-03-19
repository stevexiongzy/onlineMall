package com.xzy.core.common.persistence.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xzy.core.common.constant.ExtraParam;
import com.xzy.core.common.util.ExtraParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * mybatis-plus 自动填充功能 填充basePo字段
 * @author xzy
 */
@Slf4j
@Component
public class MetaDataHandler implements MetaObjectHandler {

    private static String ID_FIELD = "id";
    private static String APP_ID_FIELD = "appId";
    private static String DELETED_FIELD = "deleted";
    private static String CREATE_USER_ID_FIELD = "createUserId";
    private static String CREATE_USER_NAME_FIELD = "createUserName";
    private static String CREATE_TIME_FIELD = "createTime";
    private static String MODIFY_USER_ID_FIELD = "modifyUserId";
    private static String MODIFY_USER_NAME_FIELD = "modifyUserName";
    private static String MODIFY_TIME_FIELD = "modifyTime";

    private Boolean DEFAULT_DELETE = false;

    @Override
    public void insertFill(MetaObject metaObject) {
        ExtraParam extraParam = ExtraParamUtil.getExtraParam();
        /**
         * id 默认使用雪花算法插入
         */
        setFieldValByName(ID_FIELD, Optional.ofNullable(getFieldValByName(ID_FIELD, metaObject)).
                orElse(1) , metaObject);
        /**
         * appId 应用id 前端传递
         */
        setFieldValByName(APP_ID_FIELD, Optional.ofNullable(getFieldValByName(APP_ID_FIELD, metaObject)).
                orElseGet(extraParam::getAppId) , metaObject);
        /**
         * deleted 逻辑删除标识位 默认为 false 0
         */
        setFieldValByName(DELETED_FIELD, Optional.ofNullable(getFieldValByName(DELETED_FIELD, metaObject)).
                orElseGet(() -> DEFAULT_DELETE ) , metaObject);
        /**
         * createUserId 用户id 前端传递
         */
        setFieldValByName(CREATE_USER_ID_FIELD, Optional.ofNullable(getFieldValByName(CREATE_USER_ID_FIELD, metaObject)).
                orElseGet(() -> 1L ) , metaObject);
        /**
         * createUserName 用户名称 前端传递
         */
        setFieldValByName(CREATE_USER_NAME_FIELD, Optional.ofNullable(getFieldValByName(CREATE_USER_NAME_FIELD, metaObject)).
                orElseGet(() -> "username" ) , metaObject);
        /**
         * createTime 当前时间
         */
        setFieldValByName(CREATE_TIME_FIELD, Optional.ofNullable(getFieldValByName(CREATE_TIME_FIELD, metaObject)).
                orElseGet(LocalDateTime::now) , metaObject);
        /**
         * modifyUserId 修改用户id
         */
        setFieldValByName(MODIFY_USER_ID_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_USER_ID_FIELD, metaObject)).
                orElseGet(() -> 1L ) , metaObject);
        /**
         * modifyUserName 修改用户名
         */
        setFieldValByName(MODIFY_USER_NAME_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_USER_NAME_FIELD, metaObject)).
                orElseGet(() -> "username" ) , metaObject);
        /**
         * modifyTime 修改时间
         */
        setFieldValByName(MODIFY_TIME_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_TIME_FIELD, metaObject)).
                orElseGet(LocalDateTime::now) , metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        /**
         * modifyUserId 修改用户id
         */
        setFieldValByName(MODIFY_USER_ID_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_USER_ID_FIELD, metaObject)).
                orElseGet(() -> 1L ) , metaObject);
        /**
         * modifyUserName 修改用户名
         */
        setFieldValByName(MODIFY_USER_NAME_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_USER_NAME_FIELD, metaObject)).
                orElseGet(() -> "username" ) , metaObject);
        /**
         * modifyTime 修改时间
         */
        setFieldValByName(MODIFY_TIME_FIELD, Optional.ofNullable(getFieldValByName(MODIFY_TIME_FIELD, metaObject)).
                orElseGet(LocalDateTime::now) , metaObject);
    }
}
