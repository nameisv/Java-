package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Fix;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * 报修相关服务类
 */
public interface FixService {
    
    /**
     * 投诉报修分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 投诉报修保存
     * @param fix
     * @return
     */
    Integer add(Fix fix);

    /**
     * 投诉报修更新
     * @param fix
     * @return
     */
    Integer update(Fix fix);

    /**
     * 报修删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Fix selectById(Integer id);



}
