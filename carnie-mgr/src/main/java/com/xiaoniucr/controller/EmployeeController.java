package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 员工（技师）管理
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController extends BaseController {


    /**
     * 员工管理页
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以访问员工管理
        if(session.getRole() != 0) {
            return "redirect:/index.html";
        }
        return "/admin/employee/list";
    }


    /**
     * 添加页面
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以访问
        if(session.getRole() != 0) {
            return "redirect:/index.html";
        }
        return "/admin/employee/add";
    }



    /**
     * 编辑页面
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以访问
        if(session.getRole() != 0) {
            return "redirect:/index.html";
        }
        return "/admin/employee/edit";
    }


    /**
     * 员工（技师）数据查询
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以查询员工列表
        if(session.getRole() != 0) {
            return new PageVo(); // 返回空数据
        }
        PageQueryDto queryDto = new PageQueryDto(map);
        return employeeService.page(queryDto);
    }


    /**
     * 根据ID查询员工（技师）
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以查询其他员工信息
        if(session.getRole() != 0) {
            return JSONReturn.fail("无权限访问");
        }
        Employee employee = employeeService.selectById(id);
        return JSONReturn.success("查询成功",employee);
    }



    /**
     * 添加员工（技师）
     *
     * @param employee
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody Employee employee) {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以添加员工
        if(session.getRole() != 0) {
            return JSONReturn.fail("无权限操作");
        }
        return employeeService.add(employee);
    }



    /**
     * 修改员工（技师）
     * @param employee
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(@RequestBody Employee employee) {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以修改员工信息
        if(session.getRole() != 0) {
            return JSONReturn.fail("无权限操作");
        }
        Integer rows = employeeService.update(employee);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }



    /**
     * 根据ID删除员工（技师）
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");
        // 只有管理员(role=0)可以删除员工
        if(session.getRole() != 0) {
            return JSONReturn.fail("无权限操作");
        }
        Integer rows = employeeService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }


    /**
     * 获取员工（技师）个人信息
     * @return
     */
    @GetMapping(value = "/info")
    @ResponseBody
    public JSONReturn info(){

        LoginSession session = (LoginSession) getSession("user");
        Employee employee = employeeService.selectById(session.getId());
        return JSONReturn.success("查询成功！",employee);
    }


    /**
     * 根据服务项目查询技师
     * @return
     */
    @GetMapping(value = "/all/{servicesId}")
    @ResponseBody
    public JSONReturn all(@PathVariable(value = "servicesId")Integer servicesId){
        List<Employee> employees = employeeService.findByServices(servicesId);
        return JSONReturn.success("查询成功！",employees);

    }
}