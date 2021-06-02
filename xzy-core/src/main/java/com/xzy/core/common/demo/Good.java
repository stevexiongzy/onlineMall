package com.xzy.core.common.demo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzy.core.framework.persistence.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@TableName(value = "good_copy1")
public class Good extends BasePo {
    public static int a = 0;
    public Good() {}
    public Good(Long id) {
        this.id = id;
    }

    @TableField(value = "name_ext")
    private String nameExt;
    private String no;
    private Integer gender;

}
