package com.atguigu.gmall.service;

import com.atguigu.gmall.product.AttrInfo;
import com.atguigu.gmall.product.AttrValue;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
public interface AttrInfoService {
    List attrInfoList(String category1Id, String category2Id, String category3Id);

    void saveAttrInfo(AttrInfo attrInfo);

    AttrInfo getAttrValueList(Long attrId);

    List<AttrValue> getAttrValueLists(Long attrId);
}
