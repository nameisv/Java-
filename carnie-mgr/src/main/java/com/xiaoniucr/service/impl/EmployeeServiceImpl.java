package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Employee;
import com.xiaoniucr.mapper.EmployeeMapper;
import com.xiaoniucr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 员工（技师）服务实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public PageVo page(Map map) {
        List<Employee> list = employeeMapper.findList(map);
        Integer total = employeeMapper.findTotal(map);
        PageVo pageVo = new PageVo(total,list);
        if(map.get("draw") != null){
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public JSONReturn add(Employee employee) {
        //工号查询
        Employee exist = employeeMapper.selectByUsername(employee.getUsername());
        if(exist != null){
            return JSONReturn.fail("工号已存在，请重新输入！");
        }
        Date date = new Date();
        employee.setCreateTime(date);
        employee.setUpdateTime(date);
        employee.setStatus(0);
        int rows = employeeMapper.insert(employee);
        return rows > 0 ? JSONReturn.success("操作成功！") : JSONReturn.fail("操作失败！");
    }

    @Override
    public Integer update(Employee employee) {
        employee.setUpdateTime(new Date());
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public Integer del(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Employee selectById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> findByServices(Integer servicesId) {
        return employeeMapper.findByServices(servicesId);
    }

    @Override
    public Integer countNum() {
        return employeeMapper.countNum();
    }
}
