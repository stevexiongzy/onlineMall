package com.xzy.core.common.demo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzy
 */
@Slf4j
public class ExcelListener<E> extends AnalysisEventListener<E> {

    private static final int DATA_SIZE = 10;
    private List<E> dataList;

    public ExcelListener(){
       this(DATA_SIZE);
    }

    public ExcelListener(int size){
        dataList = new ArrayList<>(size);
    }


    @Override
    public void invoke(E data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成 共"+dataList.size()+"条数据");
    }

    public List<E> getDatas() {
        return dataList;
    }
}
