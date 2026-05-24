package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Transaction;
import com.xiaoniucr.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    /**
     * 用户管理页
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        return "/admin/user/list";
    }


    /**
     * 添加页面
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        return "/admin/user/add";
    }



    /**
     * 编辑页面
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        return "/admin/user/edit";
    }



    /**
     * 用户数据查询
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        PageQueryDto queryDto = new PageQueryDto(map);
        return userService.page(queryDto);
    }


    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        User user = userService.selectById(id);
        return JSONReturn.success("查询成功",user);
    }



    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(@RequestBody User user) {
        return userService.add(user);
    }



    /**
     * 修改用户
     * @param user
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(@RequestBody User user) {
        Integer rows = userService.update(user);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }



    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        Integer rows = userService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }


    /**
     * 获取用户患者个人信息
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/info")
    public JSONReturn info(){
        LoginSession session = (LoginSession) getSession("user");
        User user = userService.selectById(session.getId());
        return JSONReturn.success("查询成功！",user);
    }



    /**
     * 充值
     * @param user
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/invest")
    public JSONReturn invest(@RequestBody User user) {
        Integer rows = userService.invest(user);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }

    /**
     * 用户充值余额
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/recharge")
    public JSONReturn recharge(@RequestBody Map<String, Object> params) {
        Integer id = Integer.valueOf(params.get("id").toString());
        Double amount = Double.valueOf(params.get("amount").toString());
        
        if (amount == null || amount <= 0) {
            return JSONReturn.fail("充值金额必须大于0");
        }
        
        User user = userService.selectById(id);
        if (user == null) {
            return JSONReturn.fail("用户不存在");
        }
        
        // 计算充值后的余额
        Double newAmount = (user.getAmount() == null ? 0.0 : user.getAmount()) + amount;
        user.setAmount(newAmount);
        
        Integer rows = userService.update(user);
        if (rows > 0) {
            // 创建充值交易流水记录
            Transaction transaction = new Transaction();
            transaction.setUserId(id);
            transaction.setType(1); // 1=充值
            transaction.setAmount(amount);
            transaction.setDescription("账户充值");
            transactionService.add(transaction);
            return JSONReturn.success("充值成功！");
        } else {
            return JSONReturn.fail("充值失败");
        }
    }
}
