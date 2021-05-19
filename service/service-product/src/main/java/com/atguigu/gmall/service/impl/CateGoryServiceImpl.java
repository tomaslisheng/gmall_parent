package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.CateGoryMapper;
import com.atguigu.gmall.mapper.CateGoryMapper2;
import com.atguigu.gmall.mapper.CateGoryMapper3;
import com.atguigu.gmall.product.Category1;
import com.atguigu.gmall.product.Category2;
import com.atguigu.gmall.product.Category3;
import com.atguigu.gmall.service.CateGoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@Service
public class CateGoryServiceImpl implements CateGoryService {
    @Autowired
    private CateGoryMapper cateGoryMapper;
    @Autowired
    private CateGoryMapper2 cateGoryMapper2;
    @Autowired
    private CateGoryMapper3 cateGoryMapper3;
    public List<Category1> getCategory1(){
        return  cateGoryMapper.selectList(null);
    }

    @Override
    public List<Category2> getCategory2(String categoryId) {
        QueryWrapper<Category2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category1_id",categoryId);
        List<Category2> category2s = cateGoryMapper2.selectList(queryWrapper);
        return category2s;
    }

    @Override
    public List<Category3> getCategory3(String categoryId) {
        QueryWrapper<Category3> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category2_id",categoryId);
        List<Category3> category3s = cateGoryMapper3.selectList(queryWrapper);
        return category3s;
    }
}
