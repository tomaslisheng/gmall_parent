package com.atguigu.gmall.service;

import com.atguigu.gmall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * author lisheng
 * Date:2021/5/27
 * Description:
 */
public interface GoodRepository extends ElasticsearchRepository<Goods,Long> {
}
