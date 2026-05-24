package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Repair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RepairMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(Repair record);

    int insertSelective(Repair record);

    Repair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Repair record);

    int updateByPrimaryKey(Repair record);

    List<Repair> findList(Map map);

    Integer findTotal(Map map);
    
    Integer countUnreadRepairs();
    
    Integer countAllRepairs();
    
    Integer countRepairsSince(Long timestamp);
}
