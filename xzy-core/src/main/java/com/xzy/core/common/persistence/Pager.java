package com.xzy.core.common.persistence;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.xzy.core.common.util.DbFieldUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.NumberUtils;

import java.util.*;

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
        Long currentPageParam = NumberUtils.parseNumber(String.valueOf(param.remove("currentPage")),Long.class);
        Long pageSizeParam =  NumberUtils.parseNumber(String.valueOf(param.remove("pageSize")),Long.class);

        //分页参数
        this.currentPage = currentPageParam != null && currentPageParam > 1L ?currentPageParam :1L;
        this.pageSize = pageSizeParam != null && pageSizeParam > 1L ?pageSizeParam :10L;
        //todo:sql注入排查

        //排序参数
        String sorter = (String) param.remove("sorter");
        if(StringUtils.isBlank(sorter)){
            this.orderItems = Collections.singletonList(OrderItem.desc(DbFieldUtil.camelToUnderline("createTime")));
            return;
        }
        List<String> ascFields = Lists.newArrayList();
        List<String> descFields = Lists.newArrayList();

        String[] sorterArr = sorter.split(",");
        for(String sorterStr : sorterArr){
            String[] sf = sorterStr.split("-");
            if(sf.length == 2){
                if ("desc".equalsIgnoreCase(sf[0])) {
                    descFields.add(DbFieldUtil.camelToUnderline(sf[1]));
                } else if ("asc".equalsIgnoreCase(sf[0])) {
                    ascFields.add(DbFieldUtil.camelToUnderline(sf[1]));
                }
            }
        }

        List<OrderItem> descOrderItem = OrderItem.descs(descFields.toArray(new String[0]));
        List<OrderItem> ascOrderItem = OrderItem.ascs(ascFields.toArray(new String[0]));
        descOrderItem.addAll(ascOrderItem);
        this.orderItems = descOrderItem;
    }

    @Override
    @JsonIgnore
    public List<OrderItem> orders() {

        return orderItems;
    }

    @Override
    @JsonIgnore
    public List getRecords() {
        return list;
    }

    @Override
    @JsonIgnore
    public IPage setRecords(List records) {
        this.list = records;
        return this;
    }

    @Override
    @JsonIgnore
    public long getTotal() {
        return totalCount;
    }

    @Override
    @JsonIgnore
    public IPage setTotal(long total) {
        this.totalCount = total;
        return this;
    }

    @Override
    @JsonIgnore
    public long getSize() {
        return pageSize;
    }

    @Override
    @JsonIgnore
    public IPage setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    @JsonIgnore
    public long getCurrent() {
        return currentPage;
    }

    @Override
    @JsonIgnore
    public IPage setCurrent(long current) {
        this.currentPage = current;
        return this;
    }
}
