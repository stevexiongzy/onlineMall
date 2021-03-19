package com.xzy.core.common.demo.api;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xzy.core.common.annotation.RedisLockAnnotation;
import com.xzy.core.common.constant.RedisLockTypeEnum;
import com.xzy.core.common.demo.Good;
import com.xzy.core.common.demo.GoodMapper;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.common.persistence.IBaseService;
import com.xzy.core.common.persistence.IBaseServiceImpl;
import com.xzy.core.common.persistence.QueryParamUtil;
import com.xzy.core.common.util.ExtraParamUtil;
import com.xzy.core.common.util.RedisUtil;
import com.xzy.core.common.web.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Value("${xzy.core.dbType:MYSQL}")
    private String strtest;
    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HashOperations hashOperations;
    @Autowired
    private RedisUtil redisUtil;

    private GoodMapper goodMapper;

    IBaseService<Good> iBaseService;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public TestController(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
        this.iBaseService = new IBaseServiceImpl<GoodMapper,Good>(goodMapper);

    }

    @GetMapping("/mp")
    public ResultDTO<Good> testMP() throws IOException {
        Good good = new Good();
        good.setNameExt("insert");
        boolean save = iBaseService.save(good);
        return ResultDTO.ok(save);
    }

    @GetMapping("/mp1")
    public ResultDTO<Good> testMPUpdate() throws IOException {
        List<Good> list = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        IPage<Good> goodIPage = goodMapper.selectAllById(new Page(1,5));
        return ResultDTO.ok(goodIPage);
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
//        Good good = new Good(2L,"红楼梦","GOOD1111",23);
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
//        goodList.add(new Good(3L,"水浒传英文版人民出版社","GOOD1112",24));
//        goodList.add(new Good(4L,"数学书英文版美国出版社","GOOD11232",12));
//        goodList.add(new Good(5L,"语文书中文版人民出版社","GOOD5123",53));
//        goodList.add(new Good(6L,"英语书中文版江苏出版社","GOOD1121",54));
//        goodList.add(new Good(7L,"体育署英文版江苏出版社","GOOD5123",51));
        for(Good goodInfo :goodList){
            request.add(new IndexRequest("springboot_index_1").id(goodInfo.getId().toString())
                    .source(JSON.toJSONString(goodInfo),XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return ResultDTO.ok(response);
    }

    @GetMapping("/esearch")
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



    @GetMapping("/redis")
    public ResultDTO testRedis() throws InterruptedException {
//        Good good = new Good(2L,"红楼梦","GOOD1111",null);
//        Good good1 = new Good(3L,"西游记","12412323",null);
        HashMap map = new HashMap();
//        map.put("redisStr",good);
//        map.put("redistest",good1);
        return ResultDTO.ok();
    }

    public Object byteArrayToObj(byte[] bytes) {
        Object readObject = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
            readObject = objectInputStream.readObject();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return readObject;
    }

    public byte[] ObjToByteArray(Object obj){
        byte[] bytes = new byte[0];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
 }
