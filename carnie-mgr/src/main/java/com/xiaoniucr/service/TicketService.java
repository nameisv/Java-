package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *
 * 门票服务类
 */
public interface TicketService {
    

    /**
     * 门票分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 门票保存
     * @param ticket
     * @return
     */
    JSONReturn add(Ticket ticket);

    /**
     * 门票更新
     * @param ticket
     * @return
     */
    JSONReturn update(Ticket ticket);

    /**
     * 门票删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Ticket selectById(Integer id);
}
