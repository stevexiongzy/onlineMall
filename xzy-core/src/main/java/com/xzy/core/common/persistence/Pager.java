package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 自定义页面对象
 * @author xzy
 */
public class Pager<T> implements IPage<T> {

    /**
     * 总记录数
     **/
    @JsonProperty(index = 10)
    @ApiModelProperty(position = 10)
    private long totalCount;
    /**
     * 每页记录数
     **/
    @JsonProperty(index = 20)
    @ApiModelProperty(position = 20)
    private long pageSize;
    /**
     * 总页数
     **/
    @JsonProperty(index = 30)
    @ApiModelProperty(position = 30)
    private long totalPage;
    /**
     * 当前页数
     */
    @JsonProperty(index = 40)
    @ApiModelProperty(position = 40)
    private long currentPage;
    /**
     * 列表数据
     **/
    @JsonProperty(index = 50)
    @ApiModelProperty(position = 50)
    private List<T> list = Collections.emptyList();

    /**
     * 列表数据
     **/
    @JsonProperty(index = 60)
    @ApiModelProperty(position = 60)
    private List<OrderItem> orderItems =  Collections.emptyList();

    /**
     * 进行 count 查询 【 默认: true 】
     *
     * @return true 是 / false 否
     */
    @JsonProperty(index = 70)
    @ApiModelProperty(position = 70)
    private boolean searchCount = true;

    public Pager() {
    }

    /**
     * 构造分页参数
     * 1.构造页数和页大小参数
     * 2.构造排序参数
     * 3.删除param中相关排序参数
     * @param param
     */
    public Pager(Map<String,Object> param){
        param.get("currentPage");
    }

    @Override
    public List<OrderItem> orders() {
        return orderItems;
    }

    @Override
    public List getRecords() {
        return list;
    }

    @Override
    public IPage setRecords(List records) {
        this.list = records;
        return this;
    }

    @Override
    public long getTotal() {
        return totalCount;
    }

    @Override
    public IPage setTotal(long total) {
        this.totalCount = total;
        return this;
    }

    @Override
    public long getSize() {
        return pageSize;
    }

    @Override
    public IPage setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return currentPage;
    }

    @Override
    public IPage setCurrent(long current) {
        this.currentPage = current;
        return this;
    }
}
