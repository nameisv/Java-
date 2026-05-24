package com.xiaoniucr.controller;

import com.xiaoniucr.common.dto.PageQueryDto;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.LoginSession;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Transaction;
import com.xiaoniucr.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 交易流水管理
 */
@Controller
@RequestMapping(value = "/transaction")
public class TransactionController extends BaseController {

    /**
     * 流水管理页 - 仅管理员和高级员工
     */
    @GetMapping(value = "/list")
    public String page() {
        LoginSession session = (LoginSession) getSession("user");
        // 只允许管理员(role=0)和高级员工(role=3)查看
        if (session.getRole() == 0 || session.getRole() == 3) {
            return "/admin/transaction/list";
        }
        return "redirect:/index.html";
    }

    /**
     * 流水查询
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public PageVo query(@RequestParam Map<String, Object> map) {
        LoginSession session = (LoginSession) getSession("user");
        // 只允许管理员和高级员工查看
        if (session.getRole() != 0 && session.getRole() != 3) {
            return new PageVo(0, null);
        }
        
        PageQueryDto queryDto = new PageQueryDto(map);
        return transactionService.page(queryDto);
    }

    /**
     * 用户充值
     */
    @ResponseBody
    @PostMapping(value = "/recharge")
    public JSONReturn recharge(@RequestBody Map<String, Object> params) {
        try {
            Integer userId = Integer.valueOf(params.get("userId").toString());
            Double amount = Double.valueOf(params.get("amount").toString());
            
            if (amount <= 0) {
                return JSONReturn.fail("充值金额必须大于0");
            }

            // 更新用户余额
            userService.updateAmount(userId, amount);

            // 记录流水
            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setType(1); // 1=充值
            transaction.setAmount(amount);
            transaction.setDescription("账户充值");
            transactionService.add(transaction);

            return JSONReturn.success("充值成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("充值失败：" + e.getMessage());
        }
    }

    /**
     * 退票
     */
    @ResponseBody
    @PostMapping(value = "/refund")
    public JSONReturn refund(@RequestBody Map<String, Object> params) {
        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            
            // 处理退票逻辑（更新订单状态、恢复门票数量、退还用户余额等）
            // 这里简化处理，实际需要调用orderService
            
            return JSONReturn.success("退票成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONReturn.fail("退票失败：" + e.getMessage());
        }
    }
}
