package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.LoginDto;
import com.xiaoniucr.common.dto.UpdatePwdDto;
import com.xiaoniucr.common.vo.CountVo;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.entity.Admin;
import com.xiaoniucr.entity.Employee;
import com.xiaoniucr.entity.Feedback;
import com.xiaoniucr.entity.User;
import com.xiaoniucr.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * 登录相关接口
 */
@Controller
public class LoginController extends BaseController {


    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }


    @RequestMapping("/register.html")
    public String register() {
        return "register";
    }


    /**
     * 用户登录
     *
     * @param loginDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONReturn login(@RequestBody LoginDto loginDto, HttpSession session) {
        return userService.login(loginDto, session);
    }


    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public JSONReturn register(@RequestBody User user){
        return userService.add(user);
    }



    /**
     * 桌面页，统计以下数据信息
     *
     * @param map
     * @return
     */
    @RequestMapping("/index.html")
    public String main(ModelMap map) {

        LoginSession session = (LoginSession) getSession("user");
        Integer role = session.getRole();
        
        if(role == 0 || role == 1){
            //管理员和普通员工：显示首页统计
            Date date = new Date();
            String beginTime = DateUtils.dateFormat(date,DateUtils.DATE_PATTERN) + " 00:00:00";
            String endTime = DateUtils.dateFormat(date,DateUtils.DATE_PATTERN) + " 23:59:59";
            Integer userAddNum = userService.countAddNum(beginTime,endTime);
            Integer orderAddNum = orderService.countAddNum(beginTime,endTime);
            Integer feedbackAddNum = feedbackService.countAddNum(beginTime,endTime);
            // 计算今日营业总额：买票收入 - 退票 + 充值
            // 买票收入：统计今天购买的所有订单金额（包括已退票的）
            Double orderAmount = orderService.countAmount(beginTime,endTime);
            // 退票金额：只统计今天购买的订单中已退票的金额（不是从交易流水表统计，避免统计历史退票）
            Double refundAmount = orderService.countRefundAmount(beginTime,endTime);
            // 充值金额：统计今天的充值记录
            Double rechargeAmount = transactionService.sumRechargeAmount(beginTime,endTime);
            // 计算营业总额
            Double totalAmount = (orderAmount == null ? 0 : orderAmount) 
                               - (refundAmount == null ? 0 : refundAmount) 
                               + (rechargeAmount == null ? 0 : rechargeAmount);
            // 调试日志：查看实际计算值和时间范围
            System.out.println("=== 营业总额计算 ===");
            System.out.println("时间范围: " + beginTime + " 到 " + endTime);
            System.out.println("买票收入: " + orderAmount);
            System.out.println("退票金额: " + refundAmount + " (今天购买的订单中已退票的金额)");
            System.out.println("充值金额: " + rechargeAmount);
            System.out.println("营业总额: " + totalAmount);
            System.out.println("计算公式: " + orderAmount + " - " + refundAmount + " + " + rechargeAmount + " = " + totalAmount);
            map.put("userAddNum",userAddNum == null ? 0 : userAddNum);
            map.put("orderAddNum",orderAddNum == null ? 0 : orderAddNum);
            map.put("feedbackAddNum",feedbackAddNum == null ? 0 :feedbackAddNum);
            map.put("totalAmount",totalAmount);
            List<Feedback> list = feedbackService.findLatestList(6);
            map.put("feedbackList",list);
        } else if(role == 3){
            //高级员工：暂时空白
            map.put("roleType", "senior");
        } else if(role == 4){
            //维修员：暂时空白
            map.put("roleType", "repair");
        } else if(role == 2){
            //用户
        }
        return "index";
    }




    /**
     * 管理员个人信息页
     *
     * @return
     */
    @GetMapping("/admin/profile")
    public String adminProfile() {
        return "admin/profile";
    }


    /**
     * 员工个人信息页
     *
     * @return
     */
    @GetMapping("/employee/profile")
    public String employeeProfile() {
        return "employee/profile";
    }


    /**
     * 用户个人信息页
     *
     * @return
     */
    @GetMapping("/user/profile")
    public String userProfile() {
        return "user/profile";
    }


    /**
     * 密码修改页
     *
     * @return
     */
    @GetMapping("/password")
    public String password() {
        return "password";
    }


    /**
     * 修改密码
     *
     * @param updatePwdDto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/updatePwd")
    public JSONReturn updatePwd(@RequestBody UpdatePwdDto updatePwdDto) {

        LoginSession session = (LoginSession) getSession("user");
        if (!session.getPassword().equals(updatePwdDto.getOldPwd())) {
            return JSONReturn.fail("原始密码错误！");
        }
        Integer role = session.getRole();
        if (role == 0) {
            Admin admin = adminService.selectById(session.getId());
            admin.setPassword(updatePwdDto.getPassword());
            adminService.update(admin);
            return JSONReturn.success("更新成功！");
        } else if (role == 1 || role == 3 || role == 4) {
            // 普通员工、高级员工、维修员
            Employee employee = employeeService.selectById(session.getId());
            employee.setPassword(updatePwdDto.getPassword());
            employeeService.update(employee);
            return JSONReturn.success("更新成功！");
        } else {
            User user = userService.selectById(session.getId());
            user.setPassword(updatePwdDto.getPassword());
            userService.update(user);
            return JSONReturn.success("更新成功！");
        }


    }


    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        removeSession("user");
        return "redirect:/login.html";

    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public JSONReturn count(){
        CountVo countVo = userService.count();
        return JSONReturn.success("统计成功！", countVo);
    }


}
