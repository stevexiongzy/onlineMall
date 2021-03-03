package com.xzy.core.common.demo.api;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.xzy.core.common.annotation.RedisLockAnnotation;
import com.xzy.core.common.constant.RedisLockTypeEnum;
import com.xzy.core.common.demo.Good;
import com.xzy.core.common.demo.GoodMapper;
import com.xzy.core.common.persistence.IBaseService;
import com.xzy.core.common.persistence.IBaseServiceImpl;
import com.xzy.core.common.persistence.QueryConditionEnum;
import com.xzy.core.common.persistence.QueryParamUtil;
import com.xzy.core.common.web.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private GoodMapper goodMapper;

    IBaseService<Good> iBaseService;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public TestController(GoodMapper goodMapper){
        this.goodMapper = goodMapper;
        this.iBaseService = new IBaseServiceImpl<>(goodMapper);

    }

    @GetMapping("/getTest")
    public String getTest(@RequestParam("name")String name,@RequestParam("id") String id){
        if(Objects.equals(name,"xzy")) {
            int a = 1 / 0;
        }
        return name;
    }

    @GetMapping("/redisson")
    @RedisLockAnnotation(lockFiled = 0,tryTime = 0,typeEnum = RedisLockTypeEnum.ONE,lockTime = 60)
    public ResultDTO<String> testRedisson(@RequestParam("name") String name) throws IOException, InterruptedException {
        return ResultDTO.ok(redissonClient.getConfig().toJSON().toString());
    }

    @GetMapping("/mp")
    public ResultDTO<Good> testMP(@RequestParam("name")String name) throws IOException {
        Map<String,Object> param = new HashMap<>(1);
//        param.put("qy-nameExt-eq","222");
        List list = new ArrayList(Arrays.asList(name));
        param.put("qy-nameExt-in",list);
        QueryWrapper<Good> wrapper = QueryParamUtil.MapToWrapper(param, Good.class);
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("name_ext",list);
        List<Good> one = iBaseService.list(wrapper);
        return ResultDTO.ok(one);
    }
    @GetMapping("/es")
    public ResultDTO<Object> testES() throws IOException {
//        新增索引
//        CreateIndexRequest request = new CreateIndexRequest("springboot_index_1");
//        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
//        查询索引是否存在
//        GetIndexRequest request = new GetIndexRequest("springboot_index_1");
//        boolean response = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//        删除索引
//        DeleteIndexRequest request = new DeleteIndexRequest("springboot_index_1");
//        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
//        创建文档
        Good good = new Good(2L,"红楼梦","GOOD1111",23);
//        IndexRequest request = new IndexRequest("springboot_index_1");
//        request.id("1");
//        request.timeout(TimeValue.timeValueSeconds(1));
//
//        request.source(JSON.toJSONString(good), XContentType.JSON);
//
//        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
//        获取文档
//        GetRequest request = new GetRequest("springboot_index_1","1");
//        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
//        更新文档
//        UpdateRequest request = new UpdateRequest("springboot_index_1","1");
//        good.setNameExt("西游记");
//        request.doc(JSON.toJSONString(good),XContentType.JSON);
//        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
//        批量插入数据
        BulkRequest request = new BulkRequest("springboot_index_1");
        List<Good> goodList = new ArrayList<>(5);
        goodList.add(new Good(3L,"水浒传英文版人民出版社","GOOD1112",24));
        goodList.add(new Good(4L,"数学书英文版美国出版社","GOOD11232",12));
        goodList.add(new Good(5L,"语文书中文版人民出版社","GOOD5123",53));
        goodList.add(new Good(6L,"英语书中文版江苏出版社","GOOD1121",54));
        goodList.add(new Good(7L,"体育署英文版江苏出版社","GOOD5123",51));
        for(Good goodInfo :goodList){
            request.add(new IndexRequest("springboot_index_1").id(goodInfo.getId().toString())
                    .source(JSON.toJSONString(goodInfo),XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return ResultDTO.ok(response);
    }

    @GetMapping("/esearch")
    @Async
    public ResultDTO<Object> esearch() throws IOException {
        SearchRequest request = new SearchRequest("springboot_index_1");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.filter().;
//        searchSourceBuilder.query();

        searchSourceBuilder.query(QueryBuilders.termQuery("nameExt","西游"));
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("查询语句："+ searchSourceBuilder.toString());
        return ResultDTO.ok(response);
    }
}
