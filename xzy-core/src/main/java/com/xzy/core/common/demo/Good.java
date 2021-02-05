package com.xzy.core.common.demo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@TableName(value = "good_copy1")
public class Good {
    public Good() {}
    public Good(Long id) {
        this.id = id;
    }

    @TableId
    private Long id ;
    @TableField(value = "name_ext")
    private String nameExt;
    private String no;
    private int gender;
}
