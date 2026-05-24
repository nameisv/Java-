package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员控制器
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {


    /**
     * 管理员查询分页
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageVo adminPage(@RequestParam Map<String,Object> map){

        PageQueryDto queryDto = new PageQueryDto(map);
        return adminService.page(queryDto);
    }

    /**
     * 根据ID查询管理员
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public JSONReturn get(@PathVariable(value = "id")Integer id){
        Admin admin = adminService.selectById(id);
        return JSONReturn.success("查询成功！",admin);
    }



    /**
     * 修改管理员
     * @param admin
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONReturn update(@RequestBody Admin admin) {

        Integer rows = adminService.update(admin);
        return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
    }




    /**
     * 获取当前管理员个人信息
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public JSONReturn info(){

        LoginSession session = (LoginSession) getSession("user");
        Admin admin = adminService.selectById(session.getId());
        return JSONReturn.success("查询成功！",admin);
    }


}
