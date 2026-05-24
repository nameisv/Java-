package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Repair;
import com.xiaoniucr.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 报修管理
 */
@Controller
@RequestMapping(value = "/repair")
public class RepairController extends BaseController {

    @Autowired
    private RepairService repairService;

    /**
     * 普通员工报修页面
     */
    @GetMapping(value = "/employee/list")
    public String employeePage() {
        return "/employee/repair/list";
    }

    /**
     * 维修员报修页面
     */
    @GetMapping(value = "/repairlist")
    public String repairPage() {
        return "/repair/list";
    }

    /**
     * 高级员工报修页面
     */
    @GetMapping(value = "/senior/list")
    public String seniorPage() {
        return "/senior/repair/list";
    }

    /**
     * 报修数据查询
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        LoginSession session = (LoginSession) getSession("user");
        if (session == null) {
            // 返回空数据而不是抛出异常
            return new PageVo(0, new java.util.ArrayList<>());
        }
        
        Integer role = session.getRole();
        
        // 普通员工只能看到自己提交的报修
        if (role == 1) {
            map.put("employeeId", session.getId());
        }
        // 维修员只能看到分配给自己的报修或待受理的报修
        else if (role == 4) {
            // 不添加过滤条件，查询所有报修
        }
        // 高级员工可以看到所有报修
        else if (role == 3) {
            // 不添加过滤条件，查询所有报修
        }
        
        PageQueryDto queryDto = new PageQueryDto(map);
        return repairService.page(queryDto);
    }

    /**
     * 根据ID查询报修
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id") Integer id) {
        Repair repair = repairService.selectById(id);
        return JSONReturn.success("查询成功", repair);
    }

    /**
     * 添加报修
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody Repair repair) {
        LoginSession session = (LoginSession) getSession("user");
        if (session == null) {
            return JSONReturn.fail("请先登录！");
        }
        repair.setEmployeeId(session.getId());
        repair.setEmployeeName(session.getNickname());
        return repairService.add(repair);
    }

    /**
     * 删除报修
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        Integer rows = repairService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }

    /**
     * 受理报修
     */
    @ResponseBody
    @PutMapping(value = "/receive/{id}")
    public JSONReturn receive(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");
        if (session == null) {
            return JSONReturn.fail("请先登录！");
        }
        return repairService.receive(id, session.getId(), session.getNickname());
    }

    /**
     * 完成报修
     */
    @ResponseBody
    @PutMapping(value = "/complete/{id}")
    public JSONReturn complete(@PathVariable(value = "id") Integer id) {
        return repairService.complete(id);
    }

    /**
     * 获取未读报修数量（待受理）- 维修员专用
     */
    @ResponseBody
    @GetMapping(value = "/unreadCount")
    public JSONReturn unreadCount() {
        try {
            LoginSession session = (LoginSession) getSession("user");
            // 只有维修员(role=4)才有未读统计
            if(session != null && session.getRole() == 4) {
                Integer count = repairService.countUnreadRepairs();
                return JSONReturn.success("查询成功", count != null ? count : 0);
            }
            return JSONReturn.success("查询成功", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.success("查询成功", 0);
        }
    }

    /**
     * 高级员工标记已查看报修
     */
    @ResponseBody
    @PostMapping(value = "/senior/markViewed")
    public JSONReturn markViewed() {
        try {
            LoginSession session = (LoginSession) getSession("user");
            if(session != null && session.getRole() == 3) {
                // 在session中记录查看时间戳
                getRequest().getSession().setAttribute("seniorRepairViewTime", System.currentTimeMillis());
                return JSONReturn.success("标记成功");
            }
            return JSONReturn.fail("无权限");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("操作失败");
        }
    }

    /**
     * 获取高级员工未读报修数量
     */
    @ResponseBody
    @GetMapping(value = "/seniorUnreadCount")
    public JSONReturn seniorUnreadCount() {
        try {
            LoginSession session = (LoginSession) getSession("user");
            if(session != null && session.getRole() == 3) {
                // 获取上次查看时间
                Long lastViewTime = (Long) getRequest().getSession().getAttribute("seniorRepairViewTime");
                if(lastViewTime == null) {
                    // 首次查看，统计所有报修
                    Integer count = repairService.countAllRepairs();
                    return JSONReturn.success("查询成功", count != null ? count : 0);
                } else {
                    // 统计上次查看后的新报修
                    Integer count = repairService.countRepairsSince(lastViewTime);
                    return JSONReturn.success("查询成功", count != null ? count : 0);
                }
            }
            return JSONReturn.success("查询成功", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.success("查询成功", 0);
        }
    }
}
