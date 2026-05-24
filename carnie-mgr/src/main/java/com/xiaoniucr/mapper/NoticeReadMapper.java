package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.NoticeRead;
import org.apache.ibatis.annotations.Param;

/**
 * 公告阅读状态持久化层
 */
public interface NoticeReadMapper {

    /**
     * 插入阅读记录
     * @param record
     * @return
     */
    int insert(NoticeRead record);

    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(NoticeRead record);

    /**
     * 根据公告ID和用户ID查询阅读状态
     * @param noticeId
     * @param userId
     * @return
     */
    NoticeRead selectByNoticeAndUser(@Param("noticeId") Integer noticeId,
                                     @Param("userId") Integer userId);

    /**
     * 更新阅读状态
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(NoticeRead record);

    /**
     * 统计用户未读公告数量
     * 员工端:未收到且未完成的公告
     * 用户端:未知晓的公告
     * @param userId
     * @param role
     * @return
     */
    Integer countUnreadNotices(@Param("userId") Integer userId,
                               @Param("role") Integer role);

    /**
     * 标记员工收到公告
     * @param noticeId
     * @param userId
     * @return
     */
    int markAsReceived(@Param("noticeId") Integer noticeId,
                       @Param("userId") Integer userId);

    /**
     * 标记员工完成公告
     * @param noticeId
     * @param userId
     * @return
     */
    int markAsCompleted(@Param("noticeId") Integer noticeId,
                        @Param("userId") Integer userId);

    /**
     * 标记用户已知晓公告
     * @param noticeId
     * @param userId
     * @return
     */
    int markAsAcknowledged(@Param("noticeId") Integer noticeId,
                           @Param("userId") Integer userId);
}