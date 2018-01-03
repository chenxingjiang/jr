package com.shsxt.xm.service;

import com.shsxt.xm.po.BasUser;

/**
 * Created by lp on 2017/11/7.
 */
public interface IBasUserService {
    public BasUser queryBasUserByPhone(String phone);

    /**
     * 注册用户
     * @param phone
     * @param password
     */
    public  void saveUser(String phone,String password);


    public  BasUser userLoginCheck(String phone,String password);

    public  BasUser queryBasUserByUserId(Integer userId);
}
