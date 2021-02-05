package com.xzy.core.common.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author xzy
 * @Description:返回结果封装
 */
@Data
public class ResultDTO<T> implements Serializable {
    /**
     * 返回code，通常都返回000000成功
     */
    @JsonProperty(index = 10)
    private String code;

    /**
     * 返回msg，返回说明
     */
    @JsonProperty(index = 10)
    private String msg;

    /**
     * 返回数据
     */
    @JsonProperty(index = 10)
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(String code) {
        this.code = code;
    }

    public ResultDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultDTO ok(){
        ResultDTO dto = new ResultDTO<T>();
        return ok("000000","Success",dto);
    }

    public static <T> ResultDTO ok(T data){
        return ok("000000","Success",data);
    }

    public static <T> ResultDTO ok(String code, String msg, T data) {
        ResultDTO dto = new ResultDTO<T>();
        dto.code = code;
        dto.msg = msg;
        dto.data = data;
        return dto;
    }

    public static ResultDTO error() {
        return error("500","未知错误，请联系管理员");
    }

    public static ResultDTO error(String msg) {
        return error("500",msg);
    }

    public static ResultDTO error(String code, String msg) {
        ResultDTO dto = new ResultDTO<Void>();
        dto.code = code;
        dto.msg = msg;
        return dto;
    }
}
