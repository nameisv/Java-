package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Feedback;
import com.xiaoniucr.mapper.FeedbackMapper;
import com.xiaoniucr.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 反馈实现类
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public PageVo page(Map map) {
        List<Feedback> list = feedbackMapper.findList(map);
        Integer total = feedbackMapper.findTotal(map);

        // 关键修复：确保list不是null
        if(list == null) {
            list = new ArrayList<>();
        }
        if(total == null) {
            total = 0;
        }

        PageVo pageVo = new PageVo(total, list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Integer add(Feedback feedback) {
        Date date = new Date();
        feedback.setCreateTime(date);
        feedback.setUpdateTime(date);
        feedback.setStatus(0);
        feedback.setIsRead(0);
        return feedbackMapper.insert(feedback);
    }

    @Override
    public Integer update(Feedback feedback) {
        feedback.setUpdateTime(new Date());
        // 如果是员工回复，将is_read设置为0，表示用户未读
        if(feedback.getStatus() != null && feedback.getStatus() == 1) {
            feedback.setIsRead(0);
        }
        return feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public Integer del(Integer id) {
        return feedbackMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Feedback selectById(Integer id) {
        return feedbackMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer countAddNum(String beginTime, String endTime) {
        return feedbackMapper.countAddNum(beginTime,endTime);
    }

    @Override
    public List<Feedback> findLatestList(Integer limit) {
        return feedbackMapper.findLatestList(limit);
    }

    @Override
    public Integer countUnread(Integer userId) {
        return feedbackMapper.countUnread(userId);
    }

    @Override
    public Integer markAsRead(Integer id, Integer userId) {
        return feedbackMapper.markAsRead(id, userId);
    }

    @Override
    public Integer countUnreadForEmployee(Integer employeeId) {
        return feedbackMapper.countUnreadForEmployee(employeeId);
    }

    @Override
    public Integer countUnreadForSeniorEmployee() {
        return feedbackMapper.countUnreadForSeniorEmployee();
    }
}