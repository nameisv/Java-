package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Admin;

import java.util.Map;

/**
 * 管理员服务接口
 */
public interface AdminService {

    /**
     * 管理员分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 更新管理员
     * @param admin
     * @return
     */
    Integer update(Admin admin);


    /**
     * 根据ID查询管理员信息
     * @param id
     * @return
     */
    Admin selectById(Integer id);


}
