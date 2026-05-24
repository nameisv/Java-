package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Fix;

import java.util.List;
import java.util.Map;

/**
 * 报修持久化层
 */
public interface FixMapper {

    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     * @param record
     * @return
     */
    int insert(Fix record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Fix record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Fix selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Fix record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Fix record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Fix> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);
}