package com.shsxt.xm.web.controller;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.dto.BusAccountDto;
import com.shsxt.xm.dto.CallbackDto;
import com.shsxt.xm.dto.PayDto;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.po.BasUserSecurity;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBusAccountRechargeService;
import com.shsxt.xm.service.IBusAccountService;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.annotaions.IsLogin;
import com.shsxt.xm.web.model.ResultInfo;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by lp on 2017/11/14.
 */
@RequestMapping("account")
@Controller
public class BusAccountController extends  BaseController {
    @Resource
    private IBasUserSecurityService securityService;
    @Resource
    private IBusAccountRechargeService rechargeService;

    @Resource
    private IBusAccountService busAccountService;

    /**
     * 账户设置页面转发
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping("setting")
    public  String toSettingPage(HttpSession session, Model model){
        BasUser basUser= (BasUser) session.getAttribute("user");
        BasUserSecurity basUserSecurity= securityService.queryBasUserSecurityByUserId(basUser.getId());
        model.addAttribute("security",basUserSecurity);
        return "user/setting";
    }

    @RequestMapping("auth")
    public  String toAuthPage(){
         return "user/auth";
    }



    @IsLogin
    @RequestMapping("userAuth")
    @ResponseBody
    public ResultInfo userAuth(String realName,String card,String password,HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        securityService.updateBasUserSecurityStatus(realName,card,password,basUser.getId());
        return success("用户认证成功!");
    }


    /**
     * 用户充值界面转发
     * @return
     */
    @IsLogin
    @RequestMapping("rechargePage")
    public  String toRechargePage(){
        return "user/recharge";
    }


    /**
     * 添加充值记录
     * @param amount  充值金额
     * @param picVerifyCode  图片验证码
     * @param password    交易密码
     * @return
     */
    @IsLogin
    @RequestMapping("addBusAccountRechagre")
    public  String addBusAccountRechagre(BigDecimal amount,String picVerifyCode,String password
            ,HttpSession session,Model model){
        String sessionPicCode= (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(sessionPicCode)){
            model.addAttribute("msg","图片验证码已过期!");
            return "user/recharge";
        }
        if(!sessionPicCode.equals(picVerifyCode)){
            model.addAttribute("msg","图片验证码不正确!");
            return "user/recharge";
        }
        BasUser basUser= (BasUser) session.getAttribute("user");
        PayDto payDto= rechargeService.addBusAccountRechagre(amount,password,basUser.getId());
        model.addAttribute("pay",payDto);
        return "user/pay";
    }

    @IsLogin
    @RequestMapping("callback")
    public String  callback(String total_fee, String out_order_no, String sign, String trade_status,
                            HttpSession session,Model model){
        CallbackDto callbackDto=new CallbackDto();
        callbackDto.setOutOrderNo(out_order_no);
        callbackDto.setSign(sign);
        callbackDto.setTotalFee(total_fee);
        callbackDto.setTradeStatus(trade_status);
        BasUser basUser= (BasUser) session.getAttribute("user");
        callbackDto.setUserId(basUser.getId());
       // String view="user/result";
        try {
            rechargeService.updateBusAccountRechargeCallBack(callbackDto);
            model.addAttribute("msg","订单支付成功!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg","订单支付失败!");
        }
        return "user/result";
    }




    @IsLogin
    @RequestMapping("rechargeRecode")
    public  String toRechargeRecodePage(){
        return "user/recharge_record";
    }





    @RequestMapping("queryBusAccountRechargesByParams")
    @ResponseBody
    public PageList  queryBusAccountRechargesByParams(BusAccountRechargeQuery busAccountRechargeQuery,HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        busAccountRechargeQuery.setUserId(basUser.getId());
       return  rechargeService.queryBusAccountRechargesByParams(busAccountRechargeQuery);
    }




    @RequestMapping("queryAccountInfoByUserId")
    @ResponseBody
    public BusAccountDto queryAccountInfoByUserId(HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        return busAccountService.queryBusAccountInfoByUserId(basUser.getId());
    }

    @IsLogin
    @RequestMapping("accountInfo")
    public  String toAccountInfoPage(){
        return "user/account_info";
    }


}
