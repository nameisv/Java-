package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Repair;

import java.util.Map;

public interface RepairService {
    
    /**
     * 分页查询
     */
    PageVo page(Map map);
    
    /**
     * 根据ID查询
     */
    Repair selectById(Integer id);
    
    /**
     * 添加报修
     */
    JSONReturn add(Repair repair);
    
    /**
     * 删除报修
     */
    Integer del(Integer id);
    
    /**
     * 受理报修
     */
    JSONReturn receive(Integer id, Integer repairId, String repairName);
    
    /**
     * 完成报修
     */
    JSONReturn complete(Integer id);
    
    /**
     * 获取未读报修数量（待受理）
     */
    Integer countUnreadRepairs();
    
    /**
     * 统计所有报修数量
     */
    Integer countAllRepairs();
    
    /**
     * 统计指定时间后的报修数量
     */
    Integer countRepairsSince(Long timestamp);
}
