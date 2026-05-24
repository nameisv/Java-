package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Repair;
import com.xiaoniucr.mapper.RepairMapper;
import com.xiaoniucr.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Override
    public PageVo page(Map map) {
        List<Repair> list = repairMapper.findList(map);
        Integer total = repairMapper.findTotal(map);
        PageVo pageVo = new PageVo(total, list);
        if (map.get("draw") != null) {
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Repair selectById(Integer id) {
        return repairMapper.selectByPrimaryKey(id);
    }

    @Override
    public JSONReturn add(Repair repair) {
        if (repair.getTitle() == null || repair.getTitle().isEmpty()) {
            return JSONReturn.fail("报修标题不能为空！");
        }
        if (repair.getContent() == null || repair.getContent().isEmpty()) {
            return JSONReturn.fail("报修内容不能为空！");
        }
        if (repair.getArea() == null || repair.getArea().isEmpty()) {
            return JSONReturn.fail("报修区域不能为空！");
        }
        
        repair.setStatus(0); // 待受理
        repair.setCreateTime(new Date());
        repair.setUpdateTime(new Date());
        
        int rows = repairMapper.insertSelective(repair);
        return rows > 0 ? JSONReturn.success("报修申请提交成功！") : JSONReturn.fail("提交失败！");
    }

    @Override
    public Integer del(Integer id) {
        return repairMapper.deleteByPrimaryKey(id);
    }

    @Override
    public JSONReturn receive(Integer id, Integer repairId, String repairName) {
        Repair repair = repairMapper.selectByPrimaryKey(id);
        if (repair == null) {
            return JSONReturn.fail("报修记录不存在！");
        }
        if (repair.getStatus() != 0) {
            return JSONReturn.fail("该报修已被受理！");
        }
        
        repair.setRepairId(repairId);
        repair.setRepairName(repairName);
        repair.setStatus(1); // 已受理
        repair.setReceiveTime(new Date());
        repair.setUpdateTime(new Date());
        
        int rows = repairMapper.updateByPrimaryKeySelective(repair);
        return rows > 0 ? JSONReturn.success("受理成功！") : JSONReturn.fail("受理失败！");
    }

    @Override
    public JSONReturn complete(Integer id) {
        Repair repair = repairMapper.selectByPrimaryKey(id);
        if (repair == null) {
            return JSONReturn.fail("报修记录不存在！");
        }
        if (repair.getStatus() == 0) {
            return JSONReturn.fail("该报修尚未受理！");
        }
        if (repair.getStatus() == 2) {
            return JSONReturn.fail("该报修已完成！");
        }
        
        repair.setStatus(2); // 已完成
        repair.setCompleteTime(new Date());
        repair.setUpdateTime(new Date());
        
        int rows = repairMapper.updateByPrimaryKeySelective(repair);
        return rows > 0 ? JSONReturn.success("维修完成！") : JSONReturn.fail("操作失败！");
    }

    @Override
    public Integer countUnreadRepairs() {
        return repairMapper.countUnreadRepairs();
    }

    @Override
    public Integer countAllRepairs() {
        return repairMapper.countAllRepairs();
    }

    @Override
    public Integer countRepairsSince(Long timestamp) {
        return repairMapper.countRepairsSince(timestamp);
    }
}
