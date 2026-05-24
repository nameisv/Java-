package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Employee;
import com.xiaoniucr.entity.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 门票购买管理
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

    /**
     * 管理页
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        return "/admin/order/list";
    }

    /**
     * 门票购买查询
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        LoginSession session = (LoginSession) getSession("user");
        PageQueryDto queryDto = new PageQueryDto(map);
        if(session.getRole() == 1){
            Employee employee = employeeService.selectById(session.getId());
            queryDto.put("categoryId",employee.getCategoryId());
        }
        if(session.getRole() == 2){
            queryDto.put("userId",session.getId());
        }
        return orderService.page(queryDto);
    }

    /**
     * 根据ID查询门票购买
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        Order order = orderService.selectById(id);
        return JSONReturn.success("查询成功",order);
    }

    /**
     * 添加门票购买
     *
     * @param order
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody Order order) {
        LoginSession session = (LoginSession) getSession("user");
        order.setUserId(session.getId());
        return  orderService.add(order);
    }

    /**
     * 修改门票购买
     * @param order
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(@RequestBody Order order) {
        Integer rows =  orderService.update(order);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.fail("保存失败！");
    }

    /**
     * 根据ID删除门票购买（带权限控制）
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");
        // 传递角色和用户ID到service层进行权限校验
        return orderService.del(id, session.getRole(), session.getId());
    }

    /**
     * 退票（用户端）
     * @param order
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/cancel")
    public JSONReturn cancel(@RequestBody Order order) {
        return  orderService.cancel(order.getId());
    }

    /**
     * 出票（员工端）
     * @param order
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/issue")
    public JSONReturn issue(@RequestBody Order order) {
        LoginSession session = (LoginSession) getSession("user");
        return orderService.issue(order.getId(), session.getRole(), session.getId());
    }

}