package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Category;
import com.xiaoniucr.mapper.CategoryMapper;
import com.xiaoniucr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public PageVo page(Map map) {
        List<Category> list = categoryMapper.findList(map);
        Integer total = categoryMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Integer add(Category category) {
        Date date = new Date();
        category.setCreateTime(date);
        category.setUpdateTime(date);
        return categoryMapper.insert(category);

    }

    @Override
    public Integer update(Category category) {
        category.setUpdateTime(new Date());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public Integer del(Integer id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category selectById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public List<Category> findByEmployee(Integer empId) {
        return categoryMapper.findByEmployee(empId);
    }

    @Override
    public Integer countNum() {
        return categoryMapper.countNum();
    }


}
