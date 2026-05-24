package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Ticket;
import com.xiaoniucr.mapper.TicketMapper;
import com.xiaoniucr.mapper.UserMapper;
import com.xiaoniucr.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门票服务实现类
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;


    @Autowired
    private UserMapper userMapper;

    @Override
    public PageVo page(Map map) {
        List<Ticket> list = ticketMapper.findList(map);
        Integer total = ticketMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public JSONReturn add(Ticket ticket) {
        Ticket exist = ticketMapper.selectExist(ticket.getCategoryId(),ticket.getTicketDate(),ticket.getType());
        if(exist != null){
            return JSONReturn.fail("该项目当日此类型门票已发布！");
        }
        Date date = new Date();
        ticket.setRestNum(ticket.getTotalNum());
        ticket.setCreateTime(date);
        ticket.setUpdateTime(date);
        ticketMapper.insert(ticket);
        return JSONReturn.success("发布成功！");
    }

    @Override
    public JSONReturn update(Ticket ticket) {
        Ticket o = ticketMapper.selectByPrimaryKey(ticket.getId());
        if(ticket.getCategoryId() != o.getCategoryId() || ticket.getTicketDate().getTime() != o.getTicketDate().getTime() || ticket.getType() != o.getType()){
            Ticket exist = ticketMapper.selectExist(ticket.getCategoryId(),ticket.getTicketDate(),ticket.getType());
            if(exist != null){
                return JSONReturn.fail("该项目当日门票已发布！");
            }
        }
        ticket.setUpdateTime(new Date());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        return JSONReturn.success("发布成功！");
    }

    @Override
    public Integer del(Integer id) {
        return ticketMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Ticket selectById(Integer id) {
        return ticketMapper.selectByPrimaryKey(id);
    }

}
