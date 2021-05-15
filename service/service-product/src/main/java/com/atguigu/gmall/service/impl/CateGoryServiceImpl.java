package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.CateGoryMapper;
import com.atguigu.gmall.product.Category1;
import com.atguigu.gmall.service.CateGoryService;
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
    public List<Category1> getCategory1(){
        return  cateGoryMapper.selectList(null);
    }
}
