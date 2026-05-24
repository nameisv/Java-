package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 反馈留言控制器
 * 支持普通用户(role=2)、普通员工(role=1)和高级员工(role=3)
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController extends BaseController {

    /**
     * 留言列表页面
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");

        // 普通员工返回员工页面
        if(session.getRole() == 1) {
            return "admin/feedback/list_employee";
        }
        // 普通用户返回用户页面
        else if(session.getRole() == 2) {
            return "user/feedback/list";
        }
        // 高级员工返回高级员工页面(可以和员工页面一样)
        else if(session.getRole() == 3) {
            return "admin/feedback/list_employee";
        }

        // 其他角色(管理员)无权访问，返回首页
        return "redirect:/index.html";
    }

    /**
     * 添加留言页面 - 仅用户端
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许普通用户添加留言
        if(session.getRole() == 2) {
            return "user/feedback/add";
        }

        // 其他角色无权访问
        return "redirect:/index.html";
    }

    /**
     * 编辑留言页面 - 仅用户端
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        LoginSession session = (LoginSession) getSession("user");

        // 只允许普通用户编辑留言
        if(session.getRole() == 2) {
            return "user/feedback/edit";
        }

        // 其他角色无权访问
        return "redirect:/index.html";
    }

    /**
     * 回复留言页面 - 员工端和高级员工端
     * @return
     */
    @GetMapping(value = "/reply")
    public String reply() {
        LoginSession session = (LoginSession) getSession("user");

        // 允许普通员工和高级员工回复留言
        if(session.getRole() == 1 || session.getRole() == 3) {
            return "admin/feedback/reply_employee";
        }

        // 其他角色无权访问
        return "redirect:/index.html";
    }

    /**
     * 查询留言列表（Ajax）
     * 用户端：查询自己的留言
     * 员工端：查询分配给自己负责项目的留言
     * 高级员工端：查询所有留言
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        try {
            LoginSession session = (LoginSession) getSession("user");
            PageQueryDto queryDto = new PageQueryDto(map);

            if(session.getRole() == 2) {
                // 普通用户只能查看自己的留言
                queryDto.put("userId", session.getId());
            } else if(session.getRole() == 1) {
                // 普通员工：查询分配给自己负责项目的留言
                queryDto.put("employeeId", session.getId());
            } else if(session.getRole() == 3) {
                // 高级员工：查询所有留言(不添加任何筛选条件)
                // 不需要额外处理
            } else {
                // 其他角色无权访问，返回空数据
                PageVo emptyVo = new PageVo();
                emptyVo.setRecordsTotal(0);
                emptyVo.setRecordsFiltered(0);
                if(map.get("draw") != null){
                    emptyVo.setDraw(Integer.valueOf(map.get("draw").toString()));
                }
                return emptyVo;
            }

            PageVo pageVo = feedbackService.page(queryDto);

            // 确保返回的数据符合DataTables格式
            if(pageVo == null) {
                pageVo = new PageVo();
                pageVo.setRecordsTotal(0);
                pageVo.setRecordsFiltered(0);
            }

            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
            // 返回空数据而不是抛出异常
            PageVo errorVo = new PageVo();
            errorVo.setRecordsTotal(0);
            errorVo.setRecordsFiltered(0);
            if(map.get("draw") != null){
                errorVo.setDraw(Integer.valueOf(map.get("draw").toString()));
            }
            return errorVo;
        }
    }

    /**
     * 根据ID查询留言
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许用户端、员工端和高级员工端访问
            if(session.getRole() != 1 && session.getRole() != 2 && session.getRole() != 3) {
                return JSONReturn.fail("无权访问");
            }

            Feedback feedback = feedbackService.selectById(id);

            if(feedback == null) {
                return JSONReturn.fail("留言不存在");
            }

            // 如果是用户查看且有回复，标记为已读
            if(session.getRole() == 2 && feedback.getStatus() == 1 && feedback.getIsRead() == 0) {
                feedbackService.markAsRead(id, session.getId());
                // 重新查询以获取更新后的数据
                feedback = feedbackService.selectById(id);
            }

            return JSONReturn.success("查询成功", feedback);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 添加留言 - 仅用户端
     *
     * @param feedback
     * @return
     */
    @ResponseBody
    @PostMapping(value = "")
    public JSONReturn save(@RequestBody Feedback feedback) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许普通用户添加留言
            if(session.getRole() != 2) {
                return JSONReturn.fail("无权操作");
            }

            feedback.setUserId(session.getId());

            // 验证必填字段
            if(feedback.getTitle() == null || feedback.getTitle().trim().isEmpty()) {
                return JSONReturn.fail("请输入留言主题");
            }
            if(feedback.getContent() == null || feedback.getContent().trim().isEmpty()) {
                return JSONReturn.fail("请输入留言内容");
            }

            Integer rows = feedbackService.add(feedback);
            return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.fail("保存失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("保存失败：" + e.getMessage());
        }
    }

    /**
     * 修改留言
     * 用户端：编辑自己未回复的留言
     * 员工端/高级员工端：回复留言
     *
     * @param feedback
     * @return
     */
    @ResponseBody
    @PutMapping(value = "")
    public JSONReturn update(@RequestBody Feedback feedback) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许用户端、员工端和高级员工端操作
            if(session.getRole() != 1 && session.getRole() != 2 && session.getRole() != 3) {
                return JSONReturn.fail("无权操作");
            }

            if(feedback.getId() == null) {
                return JSONReturn.fail("留言ID不能为空");
            }

            Integer rows = feedbackService.update(feedback);
            return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("操作失败：" + e.getMessage());
        }
    }

    /**
     * 删除留言 - 仅用户端
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        try {
            LoginSession session = (LoginSession) getSession("user");

            // 只允许普通用户删除自己的留言
            if(session.getRole() != 2) {
                return JSONReturn.fail("无权操作");
            }

            Integer rows = feedbackService.del(id);
            return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("删除失败：" + e.getMessage());
        }
    }

    /**
     * 获取未读/待处理数量
     * role=1 普通员工: 统计待回复的留言
     * role=2 普通用户: 统计未读回复
     * role=3 高级员工: 统计所有待回复的留言
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/unreadCount")
    public JSONReturn unreadCount() {
        try {
            LoginSession session = (LoginSession) getSession("user");
            Integer count = 0;

            if(session.getRole() == 2) {
                // 普通用户：统计未读回复数量
                count = feedbackService.countUnread(session.getId());
            } else if(session.getRole() == 1) {
                // 普通员工：统计待处理留言数量
                count = feedbackService.countUnreadForEmployee(session.getId());
            } else if(session.getRole() == 3) {
                // 高级员工：统计所有待处理留言数量
                count = feedbackService.countUnreadForSeniorEmployee();
            }

            return JSONReturn.success("查询成功", count != null ? count : 0);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.success("查询成功", 0);
        }
    }
}