package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Notice;
import com.xiaoniucr.mapper.NoticeMapper;
import com.xiaoniucr.mapper.NoticeReadMapper;
import com.xiaoniucr.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公告实现类
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeReadMapper noticeReadMapper;

    @Override
    public PageVo page(Map map) {
        List<Notice> list = noticeMapper.findList(map);
        Integer total = noticeMapper.findTotal(map);
        PageVo pageVo = new PageVo(total, list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Integer add(Notice notice) {
        Date date = new Date();
        notice.setCreateTime(date);
        notice.setUpdateTime(date);
        return noticeMapper.insert(notice);
    }

    @Override
    public Integer update(Notice notice) {
        notice.setUpdateTime(new Date());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public Integer del(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Notice selectById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Notice> findTopLatestList(Integer limit) {
        return noticeMapper.findTopLatestList(limit);
    }

    @Override
    public Integer countNum() {
        return noticeMapper.countNum();
    }

    @Override
    public Integer countUnreadNotices(Integer userId, Integer role) {
        return noticeReadMapper.countUnreadNotices(userId, role);
    }

    @Override
    public Integer markAsReceived(Integer noticeId, Integer userId) {
        return noticeReadMapper.markAsReceived(noticeId, userId);
    }

    @Override
    public Integer markAsCompleted(Integer noticeId, Integer userId) {
        return noticeReadMapper.markAsCompleted(noticeId, userId);
    }

    @Override
    public Integer markAsAcknowledged(Integer noticeId, Integer userId) {
        return noticeReadMapper.markAsAcknowledged(noticeId, userId);
    }
}