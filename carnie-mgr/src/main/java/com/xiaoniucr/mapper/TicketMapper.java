package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门票持久化层
 */
public interface TicketMapper {
    
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
    int insert(Ticket record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Ticket record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Ticket selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Ticket record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Ticket record);



    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Ticket> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);



    Ticket selectExist(@Param(value = "categoryId")Integer categoryId,
                       @Param(value = "ticketDate") Date ticketDate,
                       @Param(value = "type")Integer type);

}