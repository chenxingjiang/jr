package com.shsxt.xm.web.controller;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.exceptions.ParamsExcetion;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.web.model.ResultInfo;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by lp on 2017/11/7.
 */
@Controller
@RequestMapping("user")
public class UserController extends  BaseController{

    @Resource
    private IBasUserService basUserService;

    @Resource
    private IBasUserSecurityService securityService;

    @RequestMapping("login")
    public String toLogin(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "login";
    }

    @RequestMapping("register")
    public  String toRegister(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "register";
    }

    @RequestMapping("userRegister")
    @ResponseBody
    public ResultInfo userRegister(String phone, String password, String picVerifyCode, String phoneVerifyCode, HttpSession session){
        String sessionPicVerifyCode= (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(null==sessionPicVerifyCode){
            return failed("图片验证码已失效!");
        }
        if(null!=sessionPicVerifyCode){
            if(!sessionPicVerifyCode.equals(picVerifyCode)){
                return failed("图片验证码不匹配!");
            }
        }

        String sessionPhoneVerifyCode= (String) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        Date sessionPhoneVerifyCodeExpirTime= (Date) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone);
        if(null==sessionPhoneVerifyCodeExpirTime){
            return failed("手机验证码已过期!");
        }

        if(null!=sessionPhoneVerifyCodeExpirTime){
            Date currentTime=new Date();
            long time= (currentTime.getTime()-sessionPhoneVerifyCodeExpirTime.getTime())/1000;
            if(time>180){
                return failed("手机验证码已失效!");
            }
        }
        if(!sessionPhoneVerifyCode.equals(phoneVerifyCode)){
            return failed("手机验证码不正确!");
        }
        ResultInfo resultInfo=null;
        try {
            basUserService.saveUser(phone,password);
            resultInfo=success("用户注册成功!");
            session.removeAttribute(P2pConstant.PICTURE_VERIFY_KEY);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone);
        }catch (ParamsExcetion paramsExcetion){
            resultInfo=failed(paramsExcetion.getErrorMsg());
        }catch (Exception e){
            resultInfo=failed("注册失败!");
        }
        return resultInfo;
    }


    @RequestMapping("userLogin")
    @ResponseBody
    public  ResultInfo userLogin(String phone,String password,HttpSession session){
        BasUser basUser= basUserService.userLoginCheck(phone,password);
        session.setAttribute("user",basUser);
        return success("用户登陆成功");

    }

    @RequestMapping("exit")
    public  String exit(HttpSession session){
        session.removeAttribute("user");
        return "login";
    }


    /**
     * 用户认证状态接口
     * @param session
     * @return
     */
    @RequestMapping("checkUserAuthStatus")
    @ResponseBody
    public  ResultInfo checkUserAuthStatus(HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        ResultInfo resultInfo=null;
        try {
            securityService.checkUserAuthStatus(basUser.getId());
            resultInfo=success("用户已认证!");
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            resultInfo=failed(e.getErrorMsg());
        }catch (Exception e){
            e.printStackTrace();
            resultInfo=failed(P2pConstant.OP_FAILED_MSG);
        }

        return resultInfo;


    }

}
