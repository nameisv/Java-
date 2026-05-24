package com.xiaoniucr.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Category;
import com.xiaoniucr.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目管理
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController extends BaseController {


    /**
     * 管理员 -- 项目管理
     *
     * @return
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问游乐项目管理，只有高级员工(role=3)和普通员工(role=1)可以访问
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/category/list";
    }


    /**
     * 管理员 -- 添加页面
     *
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/category/add";
    }


    /**
     * 管理员 -- 编辑页面
     *
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit() {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权访问
        if(session.getRole() == 0) {
            return "redirect:/index.html";
        }
        return "/admin/category/edit";
    }



    /**
     * 用户 -- 项目列表
     *
     * @return
     */
    @GetMapping(value = "/view")
    public String view() {
        return "/user/category/list";
    }

    /**
     * 项目查询
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        PageQueryDto queryDto = new PageQueryDto(map);
        return categoryService.page(queryDto);
    }


    /**
     * 根据ID查询项目
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public JSONReturn get(@PathVariable(value = "id") Integer id) {
        Category category = categoryService.selectById(id);
        return JSONReturn.success("查询成功", category);
    }



    /**
     * 修改个人信息
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping()
    public JSONReturn save(HttpServletRequest request){

        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作游乐项目");
        }

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 通过表单中的参数名来接收文件流（可用 file.getInputStream() 来接收输入流）
            //文件
            MultipartFile file = multipartRequest.getFile("file");
            //接收其他表单参数
            String categoryName = multipartRequest.getParameter("categoryName");
            String location = multipartRequest.getParameter("location");
            String beginTime = multipartRequest.getParameter("beginTime");
            String endTime = multipartRequest.getParameter("endTime");
            String detail = multipartRequest.getParameter("detail");
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setLocation(location);
            category.setBeginTime(beginTime);
            category.setEndTime(endTime);
            category.setDetail(detail);
            //封面
            if(file.getSize() > 0){
                //文件原始名称
                String originalFilename = file.getOriginalFilename(); // 获取源文件的名称
                //文件后缀
                String suffix = originalFilename.substring(originalFilename.indexOf("."));
                // 定义文件的唯一标识（前缀）
                String uuid = IdUtil.fastSimpleUUID();
                String rootFilePath =
                        System.getProperty("user.dir") + "/files/category/" + uuid + suffix; // 获取上传的路径
                File rootFile = new File(rootFilePath);
                if (!rootFile.getParentFile().exists()) {
                    rootFile.getParentFile().mkdirs();
                }
                FileUtil.writeBytes(file.getBytes(), rootFilePath); // 把文件写入到上传的路径
                category.setCover("/files/category/" + uuid + suffix);
            }
            int rows = categoryService.add(category);
            return rows > 0 ? JSONReturn.success("保存成功") : JSONReturn.fail("操作失败");
        }catch (IOException e){
            return JSONReturn.fail("保存失败："+e.getMessage());
        }


    }



    /**
     * 修改个人信息
     * @param request
     * @return
     */
    @ResponseBody
    @PutMapping()
    public JSONReturn update(HttpServletRequest request){

        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作游乐项目");
        }

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 通过表单中的参数名来接收文件流（可用 file.getInputStream() 来接收输入流）
            //文件
            MultipartFile file = multipartRequest.getFile("file");
            //接收其他表单参数
            Integer id = Integer.valueOf(multipartRequest.getParameter("id"));
            String categoryName = multipartRequest.getParameter("categoryName");
            String location = multipartRequest.getParameter("location");
            String beginTime = multipartRequest.getParameter("beginTime");
            String endTime = multipartRequest.getParameter("endTime");
            String detail = multipartRequest.getParameter("detail");
            Category category = categoryService.selectById(id);
            category.setCategoryName(categoryName);
            category.setLocation(location);
            category.setBeginTime(beginTime);
            category.setEndTime(endTime);
            category.setDetail(detail);
            //封面
            if(file.getSize() > 0){
                //文件原始名称
                String originalFilename = file.getOriginalFilename(); // 获取源文件的名称
                //文件后缀
                String suffix = originalFilename.substring(originalFilename.indexOf("."));
                // 定义文件的唯一标识（前缀）
                String uuid = IdUtil.fastSimpleUUID();
                String rootFilePath =
                        System.getProperty("user.dir") + "/files/category/" + uuid + suffix; // 获取上传的路径
                File rootFile = new File(rootFilePath);
                if (!rootFile.getParentFile().exists()) {
                    rootFile.getParentFile().mkdirs();
                }
                FileUtil.writeBytes(file.getBytes(), rootFilePath); // 把文件写入到上传的路径
                category.setCover("/files/category/" + uuid + suffix);
            }
            int rows = categoryService.update(category);
            return rows > 0 ? JSONReturn.success("更新成功") : JSONReturn.fail("操作失败");
        }catch (IOException e){
            return JSONReturn.fail("保存失败："+e.getMessage());
        }


    }


    /**
     * 根据ID删除项目
     *
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        LoginSession session = (LoginSession) getSession("user");
        // 管理员(role=0)无权操作
        if(session.getRole() == 0) {
            return JSONReturn.fail("管理员无权操作游乐项目");
        }

        Integer rows = categoryService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功") : JSONReturn.fail("操作失败");
    }


    /**
     * 查询所有项目
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/all")
    public JSONReturn all() {
        LoginSession session = (LoginSession) getSession("user");
        List<Category> categoryList = new ArrayList<>();
        if(session.getRole() != 1){
            categoryList = categoryService.findAll();
        }else{
            categoryList = categoryService.findByEmployee(session.getId());
        }
        return JSONReturn.success("查询成功！", categoryList);

    }



    /**
     * 根据ID查询项目
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/info")
    public String info(@RequestParam(value = "id") Integer id, ModelMap map) {
        Category category = categoryService.selectById(id);
        map.put("category",category);
        return "/user/category/info";
    }

}