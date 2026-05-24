package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Fix;
import com.xiaoniucr.mapper.FixMapper;
import com.xiaoniucr.service.FixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报修实现类
 */
@Service
public class FixServiceImpl implements FixService {

    @Autowired
    private FixMapper fixMapper;

    @Override
    public PageVo page(Map map) {
        List<Fix> list = fixMapper.findList(map);
        Integer total = fixMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Integer add(Fix fix) {
        Date date = new Date();
        fix.setCreateTime(date);
        fix.setUpdateTime(date);
        fix.setStatus(0);
        return fixMapper.insert(fix);
    }

    @Override
    public Integer update(Fix fix) {
        fix.setUpdateTime(new Date());
        return fixMapper.updateByPrimaryKeySelective(fix);
    }

    @Override
    public Integer del(Integer id) {
        return fixMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Fix selectById(Integer id) {
        return fixMapper.selectByPrimaryKey(id);
    }

}
