package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Employee;
import com.xiaoniucr.entity.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 门票管理
 */
@Controller
@RequestMapping(value = "/ticket")
public class TicketController extends BaseController {


    /**
     * 管理页
     *
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        // 所有角色都可以查看门票列表
        return "/admin/ticket/list";
    }


    /**
     * 添加页面
     *
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        return "/admin/ticket/add";
    }


    /**
     * 编辑页面
     *
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        return "/admin/ticket/edit";
    }


    /**
     * 门票查询
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        PageQueryDto queryDto = new PageQueryDto(map);
        LoginSession session = (LoginSession) getSession("user");

        // role=1 普通员工只能看到自己负责项目的门票
        if(session.getRole() == 1){
            Employee employee = employeeService.selectById(session.getId());
            queryDto.put("categoryId",employee.getCategoryId());
        }
        // role=0 管理员、role=3 高级员工可以查看所有门票

        return ticketService.page(queryDto);
    }


    /**
     * 根据ID查询门票
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        Ticket ticket = ticketService.selectById(id);
        return JSONReturn.success("查询成功",ticket);
    }



    /**
     * 添加门票
     *
     * @param ticket
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody Ticket ticket) {
        return ticketService.add(ticket);
    }



    /**
     * 修改门票
     * @param ticket
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(@RequestBody Ticket ticket) {
        return ticketService.update(ticket);
    }



    /**
     * 根据ID删除门票
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        Integer rows = ticketService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }
}