package com.shsxt.xm.service.impl;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.BasUserSecurityDao;
import com.shsxt.xm.po.BasUserSecurity;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.utils.AssertUtil;
import com.shsxt.xm.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.zip.CRC32;

/**
 * Created by lp on 2017/11/11.
 */
@Service
public class BasUserSecurityServiceImpl  implements IBasUserSecurityService {
    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private IBasUserService basUserService;

    @Override
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId) {
        return basUserSecurityDao.queryBasUserSecurityByUserId(userId);
    }

    @Override
    public void checkUserAuthStatus(Integer userId) {
        AssertUtil.isTrue(null==userId||null==basUserService.queryBasUserByUserId(userId),"用户未登录或不存在!");
        BasUserSecurity basUserSecurity= this.queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(basUserSecurity.getRealnameStatus().equals(0),"该用户未进行实名认证操作，请先执行认证处理");
    }

    @Override
    public void updateBasUserSecurityStatus(String realName, String card, String password, Integer userId) {
        /**
         * 1.基本参数校验
         *     参数非空
         * 2. 身份证号唯一
         * 3. 交易密码 加密处理
         */
        checkAuthParams(realName, card,password,userId);
        AssertUtil.isTrue(null!=basUserSecurityDao.queryBasUserSecurityByCard(card),"身份证号已存在!");
        password= MD5.toMD5(password);
        BasUserSecurity basUserSecurity=basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        basUserSecurity.setVerifyTime(new Date());
        basUserSecurity.setRealnameStatus(1);
        basUserSecurity.setRealname(realName);
        basUserSecurity.setPaymentPassword(password);
        basUserSecurity.setIdentifyCard(card);
        AssertUtil.isTrue(basUserSecurityDao.update(basUserSecurity)<1, P2pConstant.OP_FAILED_MSG);
    }

    private void checkAuthParams(String realName, String card, String password, Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(realName),"真实名称不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(card),"身份证号不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(password),"交易密码不能为空!");
        AssertUtil.isTrue(null==userId||null==basUserService.queryBasUserByUserId(userId),"用户未登录或用户不存在!");
    }
}
