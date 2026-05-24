package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Notice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 公告管理
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {

    /**
     * 公告管理页
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");

        // role=3 高级员工 - 管理端公告列表
        if(session.getRole() == 3) {
            return "/admin/notice/list";
        }
        // role=1 普通员工 - 员工端公告列表
        else if(session.getRole() == 1) {
            return "/admin/notice/list_employee";
        }
        // role=4 维修员 - 员工端公告列表
        else if(session.getRole() == 4) {
            return "/admin/notice/list_employee";
        }
        // role=2 普通用户 - 用户端公告列表
        else if(session.getRole() == 2) {
            return "/user/notice/list";
        }

        // role=0 管理员不需要公告功能,返回首页
        return "redirect:/index.html";
    }

    /**
     * 添加页面 - 仅高级员工
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许高级员工发布公告
        if(session.getRole() == 3) {
            return "/admin/notice/add";
        }

        return "redirect:/index.html";
    }

    /**
     * 编辑页面 - 仅高级员工
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许高级员工编辑公告
        if(session.getRole() == 3) {
            return "/admin/notice/edit";
        }

        return "redirect:/index.html";
    }

    /**
     * 公告查询
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        PageQueryDto queryDto = new PageQueryDto(map);
        return noticeService.page(queryDto);
    }

    /**
     * 根据ID查询公告
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        Notice notice = noticeService.selectById(id);
        return JSONReturn.success("查询成功", notice);
    }

    /**
     * 添加公告 - 仅高级员工
     * @param notice
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody Notice notice) {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许高级员工发布公告
        if(session.getRole() != 3) {
            return JSONReturn.fail("无权操作");
        }

        Integer rows = noticeService.add(notice);
        return rows > 0 ? JSONReturn.success("发布成功！") : JSONReturn.fail("操作失败！");
    }

    /**
     * 修改公告 - 仅高级员工
     * @param notice
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(@RequestBody Notice notice) {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许高级员工修改公告
        if(session.getRole() != 3) {
            return JSONReturn.fail("无权操作");
        }

        Integer rows = noticeService.update(notice);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }

    /**
     * 根据ID删除公告 - 仅高级员工
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许高级员工删除公告
        if(session.getRole() != 3) {
            return JSONReturn.fail("无权操作");
        }

        Integer rows = noticeService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }

    /**
     * 查看公告详情
     * @param id
     * @return
     */
    @GetMapping(value = "/info")
    public String info(@RequestParam(value = "id") Integer id, ModelMap map) {
        LoginSession session = (LoginSession) getSession("user");
        Notice notice = noticeService.selectById(id);
        map.put("notice", notice);

        // 根据角色返回不同页面
        if(session.getRole() == 1) {
            // 普通员工查看详情页
            return "/admin/notice/info_employee";
        } else if(session.getRole() == 2) {
            // 普通用户查看详情页
            return "/user/notice/info";
        } else if(session.getRole() == 3) {
            // 高级员工查看详情页
            return "/admin/notice/info_employee";
        } else if(session.getRole() == 4) {
            // 维修员查看详情页
            return "/admin/notice/info_employee";
        }

        return "redirect:/index.html";
    }

    /**
     * 获取未读公告数量
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/unreadCount")
    public JSONReturn unreadCount() {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只有员工(role=1)、用户(role=2)和维修员(role=4)才有未读统计
            if(session.getRole() == 1 || session.getRole() == 2 || session.getRole() == 4) {
                Integer count = noticeService.countUnreadNotices(session.getId(), session.getRole());
                return JSONReturn.success("查询成功", count != null ? count : 0);
            }

            return JSONReturn.success("查询成功", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.success("查询成功", 0);
        }
    }

    /**
     * 标记员工收到公告
     * @param noticeId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/markReceived")
    public JSONReturn markReceived(@RequestParam Integer noticeId) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许普通员工操作
            if(session.getRole() != 1) {
                return JSONReturn.fail("无权操作");
            }

            Integer rows = noticeService.markAsReceived(noticeId, session.getId());
            return rows > 0 ? JSONReturn.success("操作成功") : JSONReturn.fail("操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("操作失败:" + e.getMessage());
        }
    }

    /**
     * 标记员工完成公告
     * @param noticeId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/markCompleted")
    public JSONReturn markCompleted(@RequestParam Integer noticeId) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许普通员工操作
            if(session.getRole() != 1) {
                return JSONReturn.fail("无权操作");
            }

            Integer rows = noticeService.markAsCompleted(noticeId, session.getId());
            return rows > 0 ? JSONReturn.success("操作成功") : JSONReturn.fail("操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("操作失败:" + e.getMessage());
        }
    }

    /**
     * 标记用户已知晓公告
     * @param noticeId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/markAcknowledged")
    public JSONReturn markAcknowledged(@RequestParam Integer noticeId) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许普通用户操作
            if(session.getRole() != 2) {
                return JSONReturn.fail("无权操作");
            }

            Integer rows = noticeService.markAsAcknowledged(noticeId, session.getId());
            return rows > 0 ? JSONReturn.success("操作成功") : JSONReturn.fail("操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("操作失败:" + e.getMessage());
        }
    }
}