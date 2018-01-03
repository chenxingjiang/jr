package com.shsxt.xm.web.proxy;

import com.shsxt.xm.exceptions.AuthExcetion;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.service.IBasUserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by lp on 2017/11/16.
 */
@Component
@Aspect
public class LoginProxy {

    @Autowired
    private HttpSession session;

    @Resource
    private IBasUserService basUserService;

    @Pointcut(value = "@annotation(com.shsxt.xm.web.annotaions.IsLogin)")
    public  void pointCut(){}

    @Before(value = "pointCut()")
    public  void before(){
        BasUser basUser= (BasUser) session.getAttribute("user");
        if(null==basUser||null==basUserService
                .queryBasUserByUserId(basUser.getId())){
            throw  new AuthExcetion("用户未登录!");
        }
    }

}
