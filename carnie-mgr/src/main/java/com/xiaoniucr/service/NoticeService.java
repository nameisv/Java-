package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * 公告服务类
 */
public interface NoticeService {

    /**
     * 公告分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 公告保存
     * @param notice
     * @return
     */
    Integer add(Notice notice);

    /**
     * 公告更新
     * @param notice
     * @return
     */
    Integer update(Notice notice);

    /**
     * 公告删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Notice selectById(Integer id);

    /**
     * 查询最新的几条
     * @param limit
     * @return
     */
    List<Notice> findTopLatestList(Integer limit);

    /**
     * 统计公告总数
     * @return
     */
    Integer countNum();

    /**
     * 统计用户未读公告数量
     * @param userId 用户ID
     * @param role 用户角色
     * @return
     */
    Integer countUnreadNotices(Integer userId, Integer role);

    /**
     * 标记员工收到公告
     * @param noticeId 公告ID
     * @param userId 用户ID
     * @return
     */
    Integer markAsReceived(Integer noticeId, Integer userId);

    /**
     * 标记员工完成公告
     * @param noticeId 公告ID
     * @param userId 用户ID
     * @return
     */
    Integer markAsCompleted(Integer noticeId, Integer userId);

    /**
     * 标记用户已知晓公告
     * @param noticeId 公告ID
     * @param userId 用户ID
     * @return
     */
    Integer markAsAcknowledged(Integer noticeId, Integer userId);
}