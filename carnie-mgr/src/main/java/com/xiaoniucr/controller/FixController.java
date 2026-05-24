package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Fix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 报修相关
 */
@Controller
@RequestMapping(value = "/fix")
public class FixController extends BaseController {


    /**
     * 管理页
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问报修管理
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/fix/list";
    }


    /**
     * 添加页面
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/fix/add";
    }



    /**
     * 编辑页面
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/fix/edit";
    }



    /**
     * 报修查询
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        LoginSession session = (LoginSession) getSession("user");
        PageQueryDto queryDto = new PageQueryDto(map);

        // role=1 普通员工只能查看自己提交的报修
        if(session.getRole() == 1){
            queryDto.put("employeeId",session.getId());
        }
        // role=3 高级员工可以查看所有报修
        // role=0 管理员不应该访问这里

        return fixService.page(queryDto);
    }


    /**
     * 根据ID查询报修
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问
        if(session.getRole() == 0) {
            return JSONReturn.fail("无权限访问");
        }

        Fix fix = fixService.selectById(id);
        return JSONReturn.success("查询成功",fix);
    }



    /**
     * 添加报修
     *
     * @param fix
     * @return
     */
    @ResponseBody
    @PostMapping(value = "")
    public JSONReturn save(@RequestBody Fix fix) {

        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作报修");
        }

        // role=1 普通员工设置自己的ID
        if(session.getRole() == 1) {
            fix.setEmployeeId(session.getId());
        }
        // role=3 高级员工可以处理所有报修

        Integer rows =  fixService.add(fix);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.fail("保存失败！");
    }



    /**
     * 修改报修
     * @param fix
     * @return
     */
    @ResponseBody
    @PutMapping(value = "")
    public JSONReturn update(@RequestBody Fix fix) {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作报修");
        }

        Integer rows = fixService.update(fix);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }



    /**
     * 根据ID删除报修
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作报修");
        }

        Integer rows = fixService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }
}