package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.client.ProductFeignClient;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseTmVo;
import com.atguigu.gmall.model.list.SearchResponseVo;
import com.atguigu.gmall.service.GoodRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.atguigu.gmall.service.SerachService;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
@Service
public class SearchServiceImpl implements SerachService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private GoodRepository goodsRepository;
    @Override
    public List<JSONObject> getIndexCategory() {
        List<JSONObject> jsonObjects = productFeignClient.getIndexCategory();
        return jsonObjects;
    }

    @Override
    public void onSale(Long skuId) {
        Goods goods = productFeignClient.onSale(skuId);
        goods.setCreateTime(new Date());
        //根据获取的 上架商品信息。添加至elasticSearch
        Goods save = goodsRepository.save(goods);
        Optional<Goods> byId = goodsRepository.findById(skuId);
        Goods goods1 = byId.get();
        System.out.println(byId);
    }

    @Override
    public void cancelSale(Long skuId) {
        Goods goods = productFeignClient.onSale(skuId);
        //根据获取的 上架商品信息。添加至elasticSearch
        goodsRepository.deleteById(skuId);

    }

    /**
     * 通过热词和关键字查询
     * @param searchParam
     * @return
     */
    @Override
    public SearchResponseVo search(SearchParam searchParam) throws IOException {
        //1,封装之后的 SearchRequest
        SearchRequest searchRequest = getSearchRequest(searchParam);
        RequestOptions aDefault = RequestOptions.DEFAULT;
        //2,elasticSearch返回查询数据
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, aDefault);
        //3,解析es返回的数据封装成  SearchResponseVo
        SearchResponseVo parase = parase(searchResponse);
        return parase;
    }

    @Override
    public void createIndex(String index, String type) {
        Class<?> indexClass = null;

        try {
            indexClass = Class.forName(index);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        restTemplate.createIndex(indexClass);// 创建索引
        restTemplate.putMapping(indexClass);// 创建数据结构
    }
    /**
     * 封装之后的 SearchRequest参数
     * @param searchParam
     * @return
     */
    public SearchRequest getSearchRequest(SearchParam searchParam){
        String keyword = searchParam.getKeyword();
        Long category3Id = searchParam.getCategory3Id();
        //1，创建索引和type信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        searchRequest.types("info");
        //dsl语句封装
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //编写查询语句
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title",keyword);
            boolQueryBuilder.filter(matchQueryBuilder);
        }
        if(!StringUtils.isEmpty(category3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("category3Id",category3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }

        //商标聚合
        TermsAggregationBuilder tmIdAggBuild = AggregationBuilders.terms("tmIdAgg").field("tmId");
        searchSourceBuilder.aggregation(tmIdAggBuild.
                subAggregation(AggregationBuilders.terms("tmNameAgg").field("tmName")).
                subAggregation(AggregationBuilders.terms("tmLogoAgg").field("tmLogoUrl")));

        //属性聚合
        NestedAggregationBuilder attrsNested = AggregationBuilders.nested("attrsAgg", "attrs");
        searchSourceBuilder.aggregation(attrsNested.subAggregation(AggregationBuilders.terms("attrsIdAgg").field("attrs.attrId")).subAggregation(AggregationBuilders.terms("attrsNameAgg").field("attrs.attrName")).subAggregation(AggregationBuilders.terms("attrsValueAgg").field("attrs.attrValue")));

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(60);
        searchRequest.source(searchSourceBuilder);
        System.out.println("*****************searchSourceBuilder:"+searchSourceBuilder.toString());
        return searchRequest;
    }

    /**
     * 解析封装es返回信息
     * @param searchResponse
     * @return
     */
    public SearchResponseVo parase(SearchResponse searchResponse){
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchResponseVo searchResponseVo = new SearchResponseVo();
        List<Goods> goodsList = new ArrayList<>();
        List<SearchResponseTmVo> searchResponseTmVos = new ArrayList<>();
        //1，解析列表数据
        if(searchResponse!=null&&totalHits>0){
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();
                Goods goods = JSON.parseObject(sourceAsString,Goods.class);
                goodsList.add(goods);
            }
        }
        //2，解析品牌列表
        //searchResponse.getAggregations().asMap().get("")
        ParsedLongTerms tmIdLongTerms =(ParsedLongTerms)searchResponse.getAggregations().asMap().get("tmIdAgg");
        List<? extends Terms.Bucket> buckets = tmIdLongTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            SearchResponseTmVo searchResponseTmVo = new SearchResponseTmVo();
            long tmId = bucket.getKeyAsNumber().longValue();
            //获取名称
            ParsedStringTerms tmNameTerms =(ParsedStringTerms)bucket.getAggregations().asMap().get("tmNameAgg");
            List<? extends Terms.Bucket> buckets2 = tmNameTerms.getBuckets();
            for (Terms.Bucket bucket2 : buckets2) {
                String name = String.valueOf(bucket2.getKey());
                searchResponseTmVo.setTmName(name);
            }
            //获取名称
            ParsedStringTerms tmLogoTerms =(ParsedStringTerms)bucket.getAggregations().asMap().get("tmLogoAgg");
            List<? extends Terms.Bucket> buckets3 = tmLogoTerms.getBuckets();
            for (Terms.Bucket bucket3 : buckets3) {
                String logo = String.valueOf(bucket3.getKey());
                searchResponseTmVo.setTmLogoUrl(logo);
            }
            searchResponseTmVo.setTmId(tmId);
            searchResponseTmVos.add(searchResponseTmVo);
        }
        //3，解析 属性，属性值

        searchResponseVo.setGoodsList(goodsList);
        searchResponseVo.setTrademarkList(searchResponseTmVos);
        return searchResponseVo;
    }


    public void search(){
        SearchRequest searchRequest = new SearchRequest();
        RequestOptions aDefault = RequestOptions.DEFAULT;
        SearchResponse searchResponse = new SearchResponse();
        SearchHits hits = searchResponse.getHits();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        searchSourceBuilder.query(queryBuilder);
    }
}
