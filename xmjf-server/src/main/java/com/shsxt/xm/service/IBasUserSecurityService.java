package com.shsxt.xm.service;

import com.shsxt.xm.po.BasUserSecurity;

/**
 * Created by lp on 2017/11/11.
 */
public interface IBasUserSecurityService {

    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);

    /**
     * 根据登录用户id 查询用户认证状态
     * @param userId
     */
    public  void checkUserAuthStatus(Integer userId);


    /**
     * 用户认证方法
     * @param realName
     * @param card
     * @param password
     * @param userId
     */
    public  void updateBasUserSecurityStatus(String realName,String card,String password,Integer userId);






}
