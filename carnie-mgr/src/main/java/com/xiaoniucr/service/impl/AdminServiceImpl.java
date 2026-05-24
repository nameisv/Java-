package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Admin;
import com.xiaoniucr.mapper.AdminMapper;
import com.xiaoniucr.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理员实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageVo page(Map map) {
        List<Admin> list = adminMapper.findList(map);
        Integer total = adminMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }


    @Override
    public Integer update(Admin admin) {

        admin.setUpdateTime(new Date());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }



    @Override
    public Admin selectById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
