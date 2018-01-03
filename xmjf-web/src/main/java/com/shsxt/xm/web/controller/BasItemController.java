package com.shsxt.xm.web.controller;

import com.shsxt.xm.po.*;
import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.*;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.model.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lp on 2017/11/9.
 */
@RequestMapping("basItem")
@Controller
public class BasItemController extends  BaseController {
    @Resource
    private IBasItemService basItemService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;


    @Resource
    private ISysPictureService sysPictureService;

    @Resource
    private IBusItemLoanService busItemLoanService;

    @Resource
    private IBusAccountService busAccountService;

    @Resource
    private IBusItemInvestService busItemInvestService;



    @RequestMapping("basItemListPage")
    public  String toBasItemListPage(){
        return "item/invest_list";
    }

    /**
     * 多条件查询贷款项目
     * list
     * pageInfo
     */
    @RequestMapping("queryBasItemsByParams")
    @ResponseBody
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery){
         return  basItemService.queryBasItemsByParams(basItemQuery);
    }


    @RequestMapping("updateBasItemStatusToOpen")
    @ResponseBody
    public ResultInfo updateBasItemStatusToOpen(Integer itemId){
        basItemService.updateBasItemStatusToOpen(itemId);
        return success("项目开放成功!");
    }


    @RequestMapping("toBasItemDetailPage")
    public  String toBasItemDetailPage(Integer itemId, Model model, HttpSession session){
        BasItem basItem= basItemService.queryBasItemById(itemId);
        // 获取贷款人userId
        Integer userId=basItem.getItemUserId();
        BasUserSecurity basUserSecurity= basUserSecurityService.queryBasUserSecurityByUserId(userId);
        BusItemLoan busItemLoan=busItemLoanService.queryBusItemLoanByItemId(itemId);
        // 获取session中用户信息  userId
        BasUser basUser= (BasUser) session.getAttribute("user");
        if(null!=basUser){
            BusAccount busAccount= busAccountService.queryBusAccount(basUser.getId());
            model.addAttribute("busAccount",busAccount);
        }

        List<SysPicture> sysPictures=sysPictureService.querySysPicturesByItemId(itemId);
        model.addAttribute("pics",sysPictures);
        model.addAttribute("busItemLoan",busItemLoan);
        model.addAttribute("loanUser",basUserSecurity);
        model.addAttribute("item",basItem);
        return "item/details";
    }


    /**
     *
     * @param amount  投资金额
     * @param itemId  投资项目id
     * @param password  交易密码
     * @param session
     * @return
     */
        @RequestMapping("doInvest")
    @ResponseBody
    public  ResultInfo doInvest(BigDecimal amount,Integer itemId,String password,HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        busItemInvestService.addBusItemInvest(amount,itemId,password,basUser.getId());
        return success("项目投标成功!");
    }
}
