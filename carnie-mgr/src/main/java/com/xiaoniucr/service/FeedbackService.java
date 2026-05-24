package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 反馈相关服务类
 */
public interface FeedbackService {

    /**
     * 投诉反馈分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 投诉反馈保存
     * @param feedback
     * @return
     */
    Integer add(Feedback feedback);

    /**
     * 投诉反馈更新
     * @param feedback
     * @return
     */
    Integer update(Feedback feedback);

    /**
     * 反馈删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Feedback selectById(Integer id);

    /**
     * 统计新增
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countAddNum(@Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);

    /**
     * 查询最新
     * @param limit
     * @return
     */
    List<Feedback> findLatestList(Integer limit);

    /**
     * 统计用户未读回复数量
     * @param userId 用户ID
     * @return
     */
    Integer countUnread(Integer userId);

    /**
     * 标记某条留言为已读
     * @param id 留言ID
     * @param userId 用户ID
     * @return
     */
    Integer markAsRead(Integer id, Integer userId);

    /**
     * 统计员工待处理留言数量
     * @param employeeId 员工ID
     * @return
     */
    Integer countUnreadForEmployee(Integer employeeId);

    /**
     * 统计高级员工待处理留言数量
     * @return
     */
    Integer countUnreadForSeniorEmployee();
}