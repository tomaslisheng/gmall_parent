package com.atguigu.gmall.model.list;

import lombok.Data;

// 封装查询条件
@Data
public class SearchParam {

    // ?category3Id=61&trademark=2:华为&props=23:4G:运行内存&order=1:desc
    private Long category1Id;//三级分类id
    private Long category2Id;
    private Long category3Id;
    // trademark=2:华为  tmId:tmName
    private String trademark;//品牌

    private String keyword;//检索的关键字

    // 排序规则
    // 后台拼接：1:hotScore 2:price  前台页面传递：order=2:desc
    private String order = ""; // 1：综合排序/热点  2：价格

    //props=23:4G:运行内存
    //平台属性Id 平台属性值名称 平台属性名，
    private String[] props;//页面提交的数组

    private Integer pageNo = 1;//分页信息
    private Integer pageSize = 3;

    public Long getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
    }

    public Long getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }

    public Long getCategory3Id() {
        return category3Id;
    }

    public void setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String[] getProps() {
        return props;
    }

    public void setProps(String[] props) {
        this.props = props;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
