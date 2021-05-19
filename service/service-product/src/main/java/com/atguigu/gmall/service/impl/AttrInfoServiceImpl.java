package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.AttrInfoMapper;
import com.atguigu.gmall.mapper.AttrValueMapper;
import com.atguigu.gmall.product.AttrInfo;
import com.atguigu.gmall.product.AttrValue;
import com.atguigu.gmall.service.AttrInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Service
public class AttrInfoServiceImpl implements AttrInfoService {
    @Autowired
    private AttrInfoMapper attrInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;

    /**
     * 查询所有操作属性
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    @Override
    public List attrInfoList(String category1Id, String category2Id, String category3Id) {
        QueryWrapper<AttrInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",category3Id);
        List<AttrInfo> attrInfos = attrInfoMapper.selectList(wrapper);
        //根据属性查询属性值

        for (AttrInfo attrInfo : attrInfos) {
            Long id = attrInfo.getId();
            QueryWrapper<AttrValue> valueWrapper = new QueryWrapper<>();
            valueWrapper.eq("attr_id",id);
            List<AttrValue> attrValues = attrValueMapper.selectList(valueWrapper);
            attrInfo.setAttrValueList(attrValues);
        }

        return attrInfos;
    }

    /**
     * 操作属性和操作属性值添加
     * @param attrInfo
     */
    @Override
    public void saveAttrInfo(AttrInfo attrInfo) {
        if(attrInfo!=null){
            if(StringUtils.isEmpty(attrInfo.getId())){
                attrInfoMapper.insert(attrInfo);
                if(attrInfo.getAttrValueList().size()>0){
                    for (AttrValue attrValue : attrInfo.getAttrValueList()) {
                        attrValue.setAttrId(attrInfo.getId());
                        attrValueMapper.insert(attrValue);
                    }
                }
            }
            else{
                attrInfoMapper.updateById(attrInfo);
                QueryWrapper<AttrValue> valueWrapper = new QueryWrapper<>();
                valueWrapper.eq("attr_id",attrInfo.getId());
                attrValueMapper.delete(valueWrapper);
                if(attrInfo.getAttrValueList().size()>0){
                    for (AttrValue attrValue : attrInfo.getAttrValueList()) {
                        attrValue.setAttrId(attrInfo.getId());
                        attrValueMapper.insert(attrValue);
                    }
                }
            }

        }

    }

    /**
     * 修改功能回显
     * @param attrId
     * @return
     */
    @Override
    public AttrInfo getAttrValueList(Long attrId) {
        AttrInfo attrInfo = attrInfoMapper.selectById(attrId);
        QueryWrapper<AttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        List<AttrValue> attrValues = attrValueMapper.selectList(wrapper);
       // attrInfo.setAttrValueList(attrValues);
        return attrInfo;
    }

    @Override
    public List<AttrValue> getAttrValueLists(Long attrId) {
        AttrInfo attrInfo = attrInfoMapper.selectById(attrId);
        QueryWrapper<AttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        List<AttrValue> attrValues = attrValueMapper.selectList(wrapper);
        // attrInfo.setAttrValueList(attrValues);
        return attrValues;
    }
}
