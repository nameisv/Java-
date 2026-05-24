package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.*;
import com.xiaoniucr.mapper.*;
import com.xiaoniucr.service.OrderService;
import com.xiaoniucr.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门票购买服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private TransactionService transactionService;

    @Override
    public PageVo page(Map map) {
        List<Order> list = orderMapper.findList(map);
        Integer total = orderMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public JSONReturn add(Order order) {

        Ticket ticket = ticketMapper.selectByPrimaryKey(order.getTicketId());
        if(ticket.getRestNum() == 0){
            return JSONReturn.fail("门票已售罄！");
        }
        Double priceDouble = ticket.getPrice() * order.getTicketNum();
        Integer price = priceDouble.intValue();
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        if(priceDouble > user.getAmount()){
            return JSONReturn.fail("余额不足！");
        }
        ticket.setRestNum(ticket.getRestNum() - order.getTicketNum());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        // 余额扣除
        user.setAmount(user.getAmount() - priceDouble);
        userMapper.updateByPrimaryKeySelective(user);
        // 保存订单记录
        Date date = new Date();
        order.setPrice(price);
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setStatus(0);
        orderMapper.insert(order);
        return JSONReturn.success("购买成功！");
    }

    @Override
    public Integer update(Order order) {
        order.setUpdateTime(new Date());
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public JSONReturn del(Integer id, Integer role, Integer userId) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            return JSONReturn.fail("订单不存在！");
        }

        // 根据角色判断可删除的订单状态
        if(role == 2) {
            // 用户端：只能删除已退票(status=2)的订单
            if(order.getStatus() != 2){
                return JSONReturn.fail("只有已退票的订单才能删除！");
            }
            // 用户只能删除自己的订单
            if(!order.getUserId().equals(userId)) {
                return JSONReturn.fail("您只能删除自己的订单！");
            }
        } else if(role == 1) {
            // 员工端：可以删除已出票(status=1)或已退票(status=2)的订单
            if(order.getStatus() != 1 && order.getStatus() != 2){
                return JSONReturn.fail("只有已出票或已退票的订单才能删除！");
            }
            // 员工只能删除自己负责项目的订单
            Employee employee = employeeMapper.selectByPrimaryKey(userId);
            if(employee == null) {
                return JSONReturn.fail("员工信息不存在！");
            }
            Ticket ticket = ticketMapper.selectByPrimaryKey(order.getTicketId());
            if(ticket == null) {
                return JSONReturn.fail("门票信息不存在！");
            }
            if(!ticket.getCategoryId().equals(employee.getCategoryId())) {
                return JSONReturn.fail("您无权删除其他项目的订单！");
            }
        } else if(role == 0) {
            // 管理员端：不允许删除订单
            return JSONReturn.fail("管理员无权删除订单！");
        }

        int rows = orderMapper.deleteByPrimaryKey(id);
        return rows > 0 ? JSONReturn.success("删除成功！") : JSONReturn.fail("删除失败！");
    }

    @Override
    public Order selectById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public JSONReturn cancel(Integer id) {

        Order order = orderMapper.selectByPrimaryKey(id);
        if(order.getStatus() != 0){
            return JSONReturn.fail("该订单已出票或退票！");
        }
        // 票数更新
        Ticket ticket = ticketMapper.selectByPrimaryKey(order.getTicketId());
        ticket.setRestNum(ticket.getRestNum() + order.getTicketNum());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        // 余额更新
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        user.setAmount(user.getAmount() + order.getPrice());
        userMapper.updateByPrimaryKeySelective(user);
        // 创建退票交易流水记录
        Transaction transaction = new Transaction();
        transaction.setUserId(order.getUserId());
        transaction.setType(3); // 3=退票
        transaction.setAmount(order.getPrice().doubleValue());
        transaction.setDescription("订单退票，订单ID：" + order.getId());
        transactionService.add(transaction);
        // 订单状态更新
        order.setStatus(2);
        orderMapper.updateByPrimaryKeySelective(order);
        return JSONReturn.success("退票成功！");
    }

    @Override
    public JSONReturn issue(Integer id, Integer role, Integer userId) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            return JSONReturn.fail("订单不存在！");
        }

        // 只有员工可以出票
        if(role != 1) {
            return JSONReturn.fail("只有员工可以出票！");
        }

        // 只能对已支付的订单出票
        if(order.getStatus() != 0){
            return JSONReturn.fail("只能对已支付的订单出票！");
        }

        // 员工只能给自己负责项目的订单出票
        Employee employee = employeeMapper.selectByPrimaryKey(userId);
        if(employee == null) {
            return JSONReturn.fail("员工信息不存在！");
        }
        Ticket ticket = ticketMapper.selectByPrimaryKey(order.getTicketId());
        if(ticket == null) {
            return JSONReturn.fail("门票信息不存在！");
        }
        if(!ticket.getCategoryId().equals(employee.getCategoryId())) {
            return JSONReturn.fail("您无权给其他项目的订单出票！");
        }

        // 更新订单状态为已出票
        order.setStatus(1);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        return JSONReturn.success("出票成功！");
    }

    @Override
    public Integer countAddNum(String beginTime, String endTime) {
        return orderMapper.countAddNum(beginTime,endTime);
    }

    @Override
    public Double countAmount(String beginTime, String endTime) {
        return orderMapper.countAmount(beginTime,endTime);
    }

    @Override
    public Double countRefundAmount(String beginTime, String endTime) {
        return orderMapper.countRefundAmount(beginTime,endTime);
    }

}