package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.client.ProductFeignClient;
import com.atguigu.gmall.model.list.*;
import com.atguigu.gmall.service.GoodRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
        //??????????????? ??????????????????????????????elasticSearch
        Goods save = goodsRepository.save(goods);
        Optional<Goods> byId = goodsRepository.findById(skuId);
        Goods goods1 = byId.get();
        System.out.println(byId);
    }

    @Override
    public void cancelSale(Long skuId) {
        Goods goods = productFeignClient.onSale(skuId);
        //??????????????? ??????????????????????????????elasticSearch
        goodsRepository.deleteById(skuId);

    }

    /**
     * ??????????????????????????????
     * @param searchParam
     * @return
     */
    @Override
    public SearchResponseVo search(SearchParam searchParam) throws IOException {
        //1,??????????????? SearchRequest
        SearchRequest searchRequest = getSearchRequest(searchParam);
        RequestOptions aDefault = RequestOptions.DEFAULT;
        //2,elasticSearch??????????????????
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, aDefault);
        //3,??????es????????????????????????  SearchResponseVo
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
        restTemplate.createIndex(indexClass);// ????????????
        restTemplate.putMapping(indexClass);// ??????????????????
    }
    /**
     * ??????????????? SearchRequest??????
     * @param searchParam
     * @return
     */
    public SearchRequest getSearchRequest(SearchParam searchParam){
        String keyword = searchParam.getKeyword();
        Long category3Id = searchParam.getCategory3Id();
        //1??????????????????type??????
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        searchRequest.types("info");

        //dsl????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(60);
        //??????????????????
        //1?????????
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title",keyword);
            boolQueryBuilder.filter(matchQueryBuilder);
        }
        //2,????????????
        if(!StringUtils.isEmpty(category3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("category3Id",category3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //3?????????
        if(!StringUtils.isEmpty(searchParam.getTrademark())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("tmId",searchParam.getTrademark().split(":")[0]);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //4, ???????????? ???????????????????????????
        if(searchParam.getProps()!=null&&searchParam.getProps().length>0){

            for (int i = 0; i < searchParam.getProps().length; i++) {
                BoolQueryBuilder boolQuery = new BoolQueryBuilder();
                String[] split = searchParam.getProps()[i].split(":");
                String attrId = split[0];
                String attrValue = split[1];
                String attrName = split[2];
                TermQueryBuilder termQueryBuilderId = new TermQueryBuilder("attrs.attrId",attrId);
                TermQueryBuilder termQueryBuilderName = new TermQueryBuilder("attrs.attrValue",attrValue);
                TermQueryBuilder termQueryBuilderValue = new TermQueryBuilder("attrs.attrName",attrName);
                boolQuery.filter(termQueryBuilderId);
                boolQuery.filter(termQueryBuilderName);
                boolQuery.filter(termQueryBuilderValue);
                NestedQueryBuilder nestedQueryBuilder = new NestedQueryBuilder("attrs",boolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            }

        }


        searchSourceBuilder.query(boolQueryBuilder);
        //????????????
        TermsAggregationBuilder tmIdAggBuild = AggregationBuilders.terms("tmIdAgg").field("tmId");
        searchSourceBuilder.aggregation(tmIdAggBuild.
                subAggregation(AggregationBuilders.terms("tmNameAgg").field("tmName")).
                subAggregation(AggregationBuilders.terms("tmLogoAgg").field("tmLogoUrl")));

        //????????????
        //NestedAggregationBuilder attrsNested = AggregationBuilders.nested("attrsAgg", "attrs");
        searchSourceBuilder.aggregation(
                  AggregationBuilders.nested("attrsAgg", "attrs")
                .subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
                    .subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
                    .subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))
                )
        );

        // TODO: 2021/5/31
        //??????
        if(!StringUtils.isEmpty(searchParam.getOrder())){
            String order = searchParam.getOrder();
            String orderType = order.split(":")[0];
            String asc = order.split(":")[1];
            if("1".equals(orderType)){
                //?????????????????????????????????
                searchSourceBuilder.sort("hotScore",asc.equals("asc")? SortOrder.ASC:SortOrder.DESC);
            }
            if("2".equals(orderType)){
                //??????????????????
                searchSourceBuilder.sort("price",asc.equals("asc")? SortOrder.ASC:SortOrder.DESC);
            }
        }else{
            //??????????????????
            searchSourceBuilder.sort("hotScore", SortOrder.DESC);
        }

        searchRequest.source(searchSourceBuilder);
        System.out.println("*****************searchSourceBuilder:"+searchSourceBuilder.toString());
        return searchRequest;
    }

    /**
     * ????????????es????????????
     * @param searchResponse
     * @return
     */
    public SearchResponseVo parase(SearchResponse searchResponse){
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchResponseVo searchResponseVo = new SearchResponseVo();
        List<Goods> goodsList = new ArrayList<>();
        List<SearchResponseTmVo> searchResponseTmVos = new ArrayList<>();
        //1?????????????????????
        if(searchResponse!=null&&totalHits>0){
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();
                Goods goods = JSON.parseObject(sourceAsString,Goods.class);
                goodsList.add(goods);
            }
        }
        //2?????????????????????
        //searchResponse.getAggregations().asMap().get("")
        ParsedLongTerms tmIdLongTerms =(ParsedLongTerms)searchResponse.getAggregations().asMap().get("tmIdAgg");
        List<? extends Terms.Bucket> buckets = tmIdLongTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            SearchResponseTmVo searchResponseTmVo = new SearchResponseTmVo();
            long tmId = bucket.getKeyAsNumber().longValue();
            //????????????
            ParsedStringTerms tmNameTerms =(ParsedStringTerms)bucket.getAggregations().asMap().get("tmNameAgg");
            List<? extends Terms.Bucket> buckets2 = tmNameTerms.getBuckets();
            for (Terms.Bucket bucket2 : buckets2) {
                String name = String.valueOf(bucket2.getKey());
                searchResponseTmVo.setTmName(name);
            }
            //????????????
            ParsedStringTerms tmLogoTerms =(ParsedStringTerms)bucket.getAggregations().asMap().get("tmLogoAgg");
            List<? extends Terms.Bucket> buckets3 = tmLogoTerms.getBuckets();
            for (Terms.Bucket bucket3 : buckets3) {
                String logo = String.valueOf(bucket3.getKey());
                searchResponseTmVo.setTmLogoUrl(logo);
            }
            searchResponseTmVo.setTmId(tmId);
            searchResponseTmVos.add(searchResponseTmVo);
        }
        //3????????? ??????????????????
        List<SearchResponseAttrVo> attrsList = new ArrayList<>();
        ParsedNested attrsTerms =(ParsedNested)searchResponse.getAggregations().asMap().get("attrsAgg");
        ParsedLongTerms attrIdTerms = (ParsedLongTerms)attrsTerms.getAggregations().get("attrIdAgg");
        for (Terms.Bucket bucketId : attrIdTerms.getBuckets()) {
            SearchResponseAttrVo searchResponseAttrVo = new SearchResponseAttrVo();
            Long attrId = (Long)bucketId.getKey();
            searchResponseAttrVo.setAttrId(attrId);
            ParsedStringTerms attrNameTerms = (ParsedStringTerms)bucketId.getAggregations().get("attrNameAgg");
            String attrName = attrNameTerms.getBuckets().get(0).getKeyAsString();
            searchResponseAttrVo.setAttrName(attrName);
            ParsedStringTerms attrValueTerms = (ParsedStringTerms)bucketId.getAggregations().get("attrValueAgg");
            List<String> attrValueList = new ArrayList<>();
            for (Terms.Bucket bucket : attrValueTerms.getBuckets()) {
                String attrValue = bucket.getKeyAsString();
                attrValueList.add(attrValue);
            }
            searchResponseAttrVo.setAttrValueList(attrValueList);
            attrsList.add(searchResponseAttrVo);
        }
        searchResponseVo.setGoodsList(goodsList);
        searchResponseVo.setTrademarkList(searchResponseTmVos);
        searchResponseVo.setAttrsList(attrsList);
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
