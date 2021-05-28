package com.atguigu.gmall.model.list;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 平台属性相关对象
@Data
public class SearchResponseAttrVo implements Serializable {

    // 平台属性Id
    private Long attrId;//1
    //当前属性值的集合
    private List<String> attrValueList = new ArrayList<>();
    //属性名称
    private String attrName;//网络制式，分类

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public List<String> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<String> attrValueList) {
        this.attrValueList = attrValueList;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
}

