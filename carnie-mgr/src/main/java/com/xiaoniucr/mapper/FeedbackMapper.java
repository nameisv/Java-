package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 反馈持久化层
 */
public interface FeedbackMapper {

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
    int insert(Feedback record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Feedback record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Feedback selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Feedback record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Feedback record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Feedback> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);



    /**
     * 统计新增
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countAddNum(@Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);


    /**
     * 查询最新的反馈留言
     * @return
     */
    List<Feedback> findLatestList(@Param(value = "limit") Integer limit);


    /**
     * 统计用户未读回复数量
     * @param userId 用户ID
     * @return
     */
    Integer countUnread(@Param(value = "userId") Integer userId);


    /**
     * 标记某条留言为已读
     * @param id 留言ID
     * @param userId 用户ID
     * @return
     */
    int markAsRead(@Param(value = "id") Integer id, @Param(value = "userId") Integer userId);


    /**
     * 统计员工待处理留言数量(未回复的留言)
     * @param employeeId 员工ID
     * @return
     */
    Integer countUnreadForEmployee(@Param(value = "employeeId") Integer employeeId);

    /**
     * 统计高级员工待处理留言数量(所有未回复的留言)
     * @return
     */
    Integer countUnreadForSeniorEmployee();
}