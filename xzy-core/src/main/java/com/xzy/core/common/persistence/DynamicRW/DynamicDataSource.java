package com.xzy.core.common.persistence.DynamicRW;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
