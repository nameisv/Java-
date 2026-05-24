package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.dto.LoginDto;
import com.xiaoniucr.common.vo.*;
import com.xiaoniucr.entity.Admin;
import com.xiaoniucr.entity.Employee;
import com.xiaoniucr.entity.User;
import com.xiaoniucr.mapper.*;
import com.xiaoniucr.service.TransactionService;
import com.xiaoniucr.service.UserService;
import com.xiaoniucr.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private TransactionService transactionService;



    @Override
    public JSONReturn login(LoginDto loginDto, HttpSession session) {
        Integer role = loginDto.getRole();
        LoginSession loginSession = new LoginSession();;
        if(role == 0){
            // 管理员登录
            Admin admin = this.adminMapper.selectByUsername(loginDto.getUsername());
            if (admin == null) {
                return JSONReturn.fail("账户不存在！");
            }
            if (!admin.getPassword().equals(loginDto.getPassword())) {
                return JSONReturn.fail("密码错误！");
            }
            BeanUtils.copyProperties(admin, loginSession);
        }else if(role == 1 || role == 3 || role == 4){
            // 员工登录（普通员工、高级员工、维修员）
            Employee employee = employeeMapper.selectByUsername(loginDto.getUsername());
            if (employee == null) {
                return JSONReturn.fail("账户不存在！");
            }
            if (!employee.getPassword().equals(loginDto.getPassword())) {
                return JSONReturn.fail("密码错误！");
            }
            if(employee.getStatus() == 1){
                return JSONReturn.fail("账号已被冻结，请联系工作人员！");
            }
            // 验证角色是否匹配
            if(!employee.getRole().equals(role)){
                return JSONReturn.fail("账户角色不匹配！");
            }
            BeanUtils.copyProperties(employee, loginSession);
        }else if(role == 2){
            // 用户登录
            User user = userMapper.selectByUsername(loginDto.getUsername());
            if (user == null) {
                return JSONReturn.fail("账户不存在！");
            }
            if (!user.getPassword().equals(loginDto.getPassword())) {
                return JSONReturn.fail("密码错误！");
            }
            if(user.getStatus() == 1){
                return JSONReturn.fail("账号已被冻结，请联系工作人员！");
            }
            BeanUtils.copyProperties(user, loginSession);
        }
        loginSession.setRole(role);
        session.setAttribute("user", loginSession);
        return JSONReturn.success("登录成功！");
    }

    @Override
    public PageVo page(Map map) {
        List<User> list = userMapper.findList(map);
        Integer total = userMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public JSONReturn add(User user) {
        //用户名查询
        User exist = userMapper.selectByUsername(user.getUsername());
        if(exist != null){
            return JSONReturn.fail("用户名已存在，请重新输入！");
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setStatus(0);
        if(user.getSex() ==null){
            user.setSex(0);
        }
        user.setAmount(0.0);
        int rows = userMapper.insert(user);
        return rows > 0 ? JSONReturn.success("注册成功，欢迎使用游乐园管理系统！") : JSONReturn.fail("注册失败！");
    }

    @Override
    public Integer update(User user) {
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public Integer del(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public JSONReturn updateInfo(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return JSONReturn.success("更新成功，重新登录生效！");
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Integer countAddNum(String beginTime, String endTime) {
        return userMapper.countAddNum(beginTime,endTime);
    }

    @Override
    public CountVo count() {

        CountVo countVo = new CountVo();
        List<OrderVo> orderList = new ArrayList<>();
        String[] months = DateUtils.getLast12Months();
        List<Double> amountList = new ArrayList<>();
        for(int i = 0; i < 3; i ++ ){
            OrderVo orderVo = new OrderVo();
            if(i == 0){
                orderVo.setName("已支付");
            }else if(i == 1){
                orderVo.setName("已出票");
            }else{
                orderVo.setName("已退票");
            }
            Integer totalNum = orderMapper.countByStatus(i);
            orderVo.setValue(totalNum == null ? 0 : totalNum);
            orderList.add(orderVo);
        }
        for(String m : months){
            Date month = DateUtils.dateParse(m,"yyyy-MM");
            String firstDayOfMonth = DateUtils.getFisrtDayOfMonth(month);
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(month);
            String beginTime = firstDayOfMonth+" 00:00:00";
            String endTime = lastDayOfMonth+" 23:59:59";
            // 计算营业总额：买票收入 - 退票 + 充值
            // 买票收入：统计该月购买的所有订单金额（包括已退票的）
            Double orderAmount = orderMapper.countAmount(beginTime, endTime);
            // 退票金额：只统计该月购买的订单中已退票的金额
            Double refundAmount = orderMapper.countRefundAmount(beginTime, endTime);
            // 充值金额：统计该月的充值记录
            Double rechargeAmount = transactionService.sumRechargeAmount(beginTime, endTime);
            // 计算营业总额
            Double totalAmount = (orderAmount == null ? 0 : orderAmount) 
                               - (refundAmount == null ? 0 : refundAmount) 
                               + (rechargeAmount == null ? 0 : rechargeAmount);
            amountList.add(totalAmount);
        }
        countVo.setOrderList(orderList);
        countVo.setMonthList(months);
        countVo.setAmountList(amountList);
        return countVo;
    }

    @Override
    public Integer invest(User user) {
        User u = userMapper.selectByPrimaryKey(user.getId());
        u.setAmount(u.getAmount() + user.getAmount());
        return userMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public int updateAmount(Integer userId, Double amount) {
        return userMapper.updateAmount(userId, amount);
    }

}
