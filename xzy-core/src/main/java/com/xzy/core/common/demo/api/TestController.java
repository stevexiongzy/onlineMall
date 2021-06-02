package com.xzy.core.common.demo.api;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import com.xzy.core.common.annotation.RedisLockAnnotation;
import com.xzy.core.common.constant.RedisLockTypeEnum;
import com.xzy.core.common.demo.*;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.common.persistence.IBaseService;
import com.xzy.core.common.persistence.IBaseServiceImpl;
import com.xzy.core.common.persistence.Pager;
import com.xzy.core.common.persistence.QueryParamUtil;
import com.xzy.core.common.util.ExtraParamUtil;
import com.xzy.core.common.util.RedisUtil;
import com.xzy.core.common.web.ResultDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
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
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

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
    private GoodService goodService;
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
    @Transactional
    public ResultDTO<Good> testMPUpdate() throws IOException {
        List<Good> list = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        param.put("pageSize",3);
        param.put("currentPage",1);
        list.add(new Good());
        list.add(new Good());
        iBaseService.saveBatch(list);
        return ResultDTO.ok();
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


    /**
     * 确定是sku属性
     */
    @Value("${xzy.core.alReadySkuPro}")
    private String alReadySkuPro;

    /**
     * 确定是spu属性
     */
    @Value("${xzy.core.alReadySpuPro}")
    private String alReadySpuPro;

    /**
     * 需要判断的所有属性
     */
    @Value("${xzy.core.allProperties}")
    private String allProperties;


    @GetMapping("/redis")
    public ResultDTO testRedis() throws IOException {
        String[] alReadySkuProArr = alReadySkuPro.split(",");
        String[] alReadySpuProArr = alReadySpuPro.split(",");
        List<String[]> content = new ArrayList<>(219);
//        List<String> alReadySkuProList = Lists.newArrayList(alReadySkuProArr);
//        List<String> alReadySpuProList = Lists.newArrayList(alReadySpuProArr);

        CsvReader csvReader = new CsvReader("D:\\Download\\类目属性18-19年.csv",',', StandardCharsets.UTF_8);
        CsvWriter csvWriter = new CsvWriter("D:\\Download\\类目属性test.csv",',', StandardCharsets.UTF_8);
        // 跳过表头
        csvReader.readHeaders();

        // 读取除表头外的内容
        while (csvReader.readRecord()) {
            List<String> alReadySkuProList = Lists.newArrayList(alReadySkuProArr);
            List<String> alReadySpuProList = Lists.newArrayList(alReadySpuProArr);
            String[] values = csvReader.getValues();
            String title = values[0];
            String allProperties = values[1];
            String[] allPropertiesArr = allProperties.split(",");
            List<String> needJudgePro = Lists.newArrayList(allPropertiesArr);

            //处理
            alReadySkuProList.retainAll(needJudgePro);
            needJudgePro.removeAll(alReadySkuProList);
            needJudgePro.remove("单据日期");
            needJudgePro.remove("存货名称");
            needJudgePro.remove("外文名称");
            needJudgePro.remove("存货编码");
            needJudgePro.remove("商品条码");
            alReadySkuProList.add(0,"条形码");
            alReadySkuProList.add(0,"sku名称");
            alReadySkuProList.add(0,"sku编码");
            if(title.contains("挂件")){
                System.out.println();
            }
            alReadySpuProList.retainAll(needJudgePro);
            needJudgePro.removeAll(alReadySpuProList);
            String spuValue = String.join("，",alReadySpuProList);
            String needJudgeValue = String.join("，",needJudgePro);
            String alReadySkuProValue = String.join("，", alReadySkuProList);
            if(needJudgeValue.contains("，尺码，花色，品类，材质，系列")){
//                needJudgeValue = needJudgeValue.replaceAll("，尺码，花色，品类，材质，系列", "");
                alReadySkuProValue +="，尺码，花色，品类，材质，系列";
                if(needJudgeValue.contains("规格")){
                    spuValue+="，规格";
                }
                if(needJudgeValue.contains("产地名称")){
                    spuValue+="，产地名称";
                }
            }else{
                spuValue = spuValue+"---"+needJudgeValue;
            }
            content.add((String[]) Arrays.asList(title,spuValue, alReadySkuProValue).toArray());
        }
        csvReader.close();

        String[] headers = {"品类名称", "维度", "属性"};
        csvWriter.writeRecord(headers);

        for(String[] value : content){
            String spuPro = "spu编码，单据日期，商品名称，英文名"+value[1];
            String skuPro = value[2];
            csvWriter.writeRecord((String[]) Arrays.asList(value[0], "spu", spuPro).toArray());
            csvWriter.writeRecord((String[]) Arrays.asList(value[0], "sku", skuPro).toArray());
        }
        csvWriter.close();

        return ResultDTO.ok();
    }
    @Data
    @Builder
    static
    class DemoData {
        @ExcelProperty("存货分类编码")
        private String code;
        @ExcelProperty("存货分类名称")
        private Date name;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }
    @GetMapping("/testExcel")
    public void testExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), DemoData.class)
//                .registerWriteHandler(new CustomerExcelHandler())
//                .sheet("模板")
//                .doWrite(data());
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new CustomerExcelHandler()).build();
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(head()).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//            List<DemoData> data = data();
            List<DemoData> data = Lists.newArrayList();
            excelWriter.write(data, writeSheet);
        }
        excelWriter.finish();
        return;
    }

    @GetMapping("/testExcel1")
    public ResultDTO<Object> testExcel1(HttpServletResponse response) throws IOException {
        ExcelReader excelReader = EasyExcel.read("D:\\Download\\存货分类t.xls").build();
        ExcelReader excelReaderSearch = EasyExcel.read("D:\\Download\\业务类目基础类目存货分类对应关系.xlsx").build();
//        List<ReadSheet> sheetList = new ArrayList<>(15);
        List<ReadSheet> sheetSearchList = new ArrayList<>(4);
//        List<ExcelListener> excelListenerList = new ArrayList<>(15);
        List<ExcelListener> excelListenerSearchList = new ArrayList<>(4);
//        for(int i = 1 ; i <= 14 ; i++){
//            ExcelListener<DemoData> excelListener = new ExcelListener<>(30);
//            excelListenerList.add(excelListener);
//            sheetList.add(EasyExcel.readSheet(i).registerReadListener(excelListener).head(head()).build());
//        }
        for(int i= 5 ;i<9;i++){
            ExcelListener excelListener = new ExcelListener(50);
            excelListenerSearchList.add(excelListener);
            sheetSearchList.add(EasyExcel.readSheet(i).registerReadListener(excelListener).build());
        }
        ExcelListener<Map<Integer,String>> excelListenerSearch = new ExcelListener<>();
        excelReader.read(EasyExcel.readSheet(0).registerReadListener(excelListenerSearch).head(head()).build());
        excelReaderSearch.read(sheetSearchList);
        List<Map<Integer,String>> datas1 = excelListenerSearch.getDatas();
        Map<Object,String> data = datas1.stream().collect(Collectors.toMap(map ->map.get(0),map ->map.get(1)));

        Map<String,List<String>> searchResult = new HashMap<>();

        for(ExcelListener excelListener :excelListenerSearchList){
            List<Map<Integer,String>> datas = excelListener.getDatas();
            List<String> searchKeyList = datas.stream().map(map -> map.get(3)).collect(Collectors.toList());
            searchKeyList = searchKeyList.stream().distinct().collect(Collectors.toList());
            searchKeyList.removeAll(Collections.singleton(null));
            for (String key : searchKeyList) {
                data.forEach((k,v) ->{
                    if(v.contains(key) || key.contains(v)){
                        if(searchResult.get(key)== null){
                            searchResult.put(key, Lists.newArrayList(k+"+"+v));
                        }else {
                            searchResult.get(key).add(k+"+"+v);
                        }
                    }
                });
            }
        }

        return ResultDTO.ok(searchResult);
    }

    List<DemoData> data(){
        List<DemoData> list = new ArrayList<>(10);
        for(int i=0;i<10;i++){
//            list.add(DemoData.builder().date(new Date()).string("title"+i).doubleData(i + 1.1).build());
        }
        return list;
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("存货分类编码");
        List<String> head1 = new ArrayList<String>();
        head1.add("存货分类名称");
        list.add(head0);
        list.add(head1);
        return list;
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

    public static void main(String[] args) {
        int abcabcbb = lengthOfLongestSubstring("abcabcbb");
    }

    /**
     *  0 1 2 3 4 5 6 7
     *  a b c a b c b b
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for(int i = 0; i < s.length(); i ++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);
        }
        return max;
    }

    boolean isProValueEq(Good good1,Good good2,String properties){
        return Objects.equals(getFieldValue(good1,properties),getFieldValue(good2,properties));
    }

    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
 }
