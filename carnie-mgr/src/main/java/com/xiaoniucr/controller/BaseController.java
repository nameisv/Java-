package com.xiaoniucr.controller;

import com.xiaoniucr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 基础控制器
 */
public class BaseController {

    @Autowired
    public AdminService adminService;

    @Autowired
    public UserService userService;

    @Autowired
    public NoticeService noticeService;


    @Autowired
    public FeedbackService feedbackService;


    @Autowired
    public EmployeeService employeeService;


    @Autowired
    public CategoryService categoryService;


    @Autowired
    public TicketService ticketService;


    @Autowired
    public OrderService orderService;


    @Autowired
    public FixService fixService;

    @Autowired
    public RepairService repairService;

    @Autowired
    public TransactionService transactionService;

    /**
     * 获取session对象
     *
     * @param attributeName
     * @return
     */
    public Object getSession(String attributeName) {
        return this.getRequest().getSession(true).getAttribute(attributeName);
    }

    /**
     * 设置session
     *
     * @param attributeName
     * @param object
     */
    public void setSession(String attributeName, Object object) {
        this.getRequest().getSession(true).setAttribute(attributeName, object);
    }

    /**
     * 移除session
     * @param attributeName
     */
    public void removeSession(String attributeName){
        this.getRequest().getSession(true).removeAttribute(attributeName);
    }

    /**
     * 获取request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }


    /**
     * 获取response对象
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getResponse();
    }


    /**
     * 获取IP地址
     * @return
     */
    public String getIpAddr() {

        String ipAddress = null;
        try {
            HttpServletRequest request = this.getRequest();
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

}
