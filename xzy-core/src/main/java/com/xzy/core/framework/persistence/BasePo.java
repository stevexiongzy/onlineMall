package com.xzy.core.framework.persistence;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Description: po类的公共属性
 * @author xzy
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasePo<T extends Model<T>> extends Model<T> implements Serializable {
    /**
     * 主键 id , insert时自动注入
     */
    @TableId(value = "id")
    protected Long id;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    @TableField(value = "deleted",fill = FieldFill.INSERT)
    protected Boolean deleted;

    /**
     * 应用id
     */
    @TableField(value = "app_id",fill = FieldFill.INSERT)
    protected Long appId;

    /**
     * 多租户 租户id
     */
    @TableField(value = "tenant_id")
    protected Long tenantId;

    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id",fill = FieldFill.INSERT)
    protected Long createUserId;

    /**
     * 创建用户name
     */
    @TableField(value = "create_user_name",fill = FieldFill.INSERT)
    protected String createUserName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 修改用户id
     */
    @TableField(value = "modify_user_id",fill = FieldFill.INSERT_UPDATE)
    protected Long modifyUserId;

    /**
     * 修改用户name
     */
    @TableField(value = "modify_user_name",fill = FieldFill.INSERT_UPDATE)
    protected String modifyUserName;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime modifyTime;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
