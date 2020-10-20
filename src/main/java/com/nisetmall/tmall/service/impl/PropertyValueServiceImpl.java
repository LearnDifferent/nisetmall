package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.PropertyValueMapper;
import com.nisetmall.tmall.pojo.Product;
import com.nisetmall.tmall.pojo.Property;
import com.nisetmall.tmall.pojo.PropertyValue;
import com.nisetmall.tmall.pojo.PropertyValueExample;
import com.nisetmall.tmall.service.PropertyService;
import com.nisetmall.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;

    /**
     * @param ptid property id
     * @param pid  product id
     * @return
     */
    @Override
    public PropertyValue get(int ptid, int pid) {

        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pv = propertyValueMapper.selectByExample(example);

        if (pv.isEmpty()) {
            return null;
        }

        return pv.get(0);
    }

    @Override
    public void init(Product p) {

        //根据 product 获取 property 列表
        List<Property> pts = propertyService.list(p.getCid());

        //根据 property 列表获取该 property 分类下的所有 value
        for (Property pt : pts) {
            //调用 PropertyValue get(int ptid, int pid)，获取 property value
            PropertyValue pv = this.get(pt.getId(), p.getId());

            //如果没有值，就创建并赋值
            if (pv == null) {
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }

    }

    /**
     * .updateByPrimaryKeySelective 如果字段为 null 就忽略更新，用于更新某个字段
     * .updateByPrimaryKey 更新注入的全部字段
     * 这里是只更新 propertyValue 存在的字段
     * PropertyValue 只修改 id 和 value，不涉及 pid 和 ptid
     *
     * @param pv
     */
    @Override
    public void update(PropertyValue pv) {

        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public List<PropertyValue> list(int pid) {

        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);

        for (PropertyValue pv : pvs) {
            //根据 property id 获取 property
            Property property = propertyService.get(pv.getPtid());
            //给 property value 设置 property
            pv.setProperty(property);
        }

        return pvs;
    }
}
