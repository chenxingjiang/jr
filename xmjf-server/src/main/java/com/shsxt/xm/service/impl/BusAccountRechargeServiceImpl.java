package com.shsxt.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.BusAccountDao;
import com.shsxt.xm.db.dao.BusAccountLogDao;
import com.shsxt.xm.db.dao.BusAccountRechargeDao;
import com.shsxt.xm.dto.CallbackDto;
import com.shsxt.xm.dto.PayDto;
import com.shsxt.xm.enums.RechargeType;
import com.shsxt.xm.po.*;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.service.IBusAccountRechargeService;
import com.shsxt.xm.utils.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Created by lp on 2017/11/14.
 */
@Service
public class BusAccountRechargeServiceImpl implements IBusAccountRechargeService {
    @Resource
    private BusAccountRechargeDao accountRechargeDao;

    @Resource
    private IBasUserSecurityService securityService;
    @Resource
    private IBasUserService basUserService;

    @Resource
    private BusAccountLogDao busAccountLogDao;

    @Resource
    private BusAccountDao busAccountDao;
    @Override
    public PayDto addBusAccountRechagre(BigDecimal amount, String password, Integer userId) {
        /**
         * 1.参数基本校验
         *    金额 大于0
         *    交易密码匹配
         *     用户id 必须存在
         * 2.执行记录添加
         * 3.收集发送支付请求对应参数
         */
        checkAccountRechargeParams(amount,password,userId);
        BusAccountRecharge busAccountRecharge=new BusAccountRecharge();
        busAccountRecharge.setAddtime(new Date());
        busAccountRecharge.setRemark("PC端用户充值");
        busAccountRecharge.setType(RechargeType.WEB.getType());
        busAccountRecharge.setResource("PC端用户充值");
        busAccountRecharge.setFeeRate(BigDecimal.ZERO);
        busAccountRecharge.setFeeAmount(BigDecimal.ZERO);
        busAccountRecharge.setRechargeAmount(amount);
        String orderNo=com.shsxt.xm.utils.StringUtils.getOrderNo();
        busAccountRecharge.setOrderNo(orderNo);
        busAccountRecharge.setUserId(userId);
        busAccountRecharge.setStatus(2);
        AssertUtil.isTrue(accountRechargeDao.insert(busAccountRecharge)<1, P2pConstant.OP_FAILED_MSG);
        return buildPayInfo(orderNo,amount,"PC端用户充值","网站用户充值");
    }

    /**
     * 回调业务处理方法
     * @param callbackDto
     */
    @Override
    public void updateBusAccountRechargeCallBack(CallbackDto callbackDto) {
        BasUser basUser=basUserService.queryBasUserByUserId(callbackDto.getUserId());
        AssertUtil.isTrue(null==basUser,"用户未登录!");
        AssertUtil.isTrue(StringUtils.isBlank(callbackDto.getOutOrderNo())
                ||StringUtils.isBlank(callbackDto.getSign())
                ||StringUtils.isBlank(callbackDto.getTotalFee())
                ||StringUtils.isBlank(callbackDto.getTradeStatus()),P2pConstant.ORDER_FAILED);
        Md5Util md5Util=new Md5Util();
        //  out_order_no+total_fee+trade_status+pid+key
        String md5Sign= md5Util.encode(callbackDto.getOutOrderNo()+callbackDto.getTotalFee()
        +callbackDto.getTradeStatus()+P2pConstant.PARTNER+P2pConstant.KEY,null);
        AssertUtil.isTrue(!md5Sign.equals(callbackDto.getSign()),"签名被篡改，订单异常!");
        AssertUtil.isTrue(!P2pConstant.ORDER_SUCCESS.equals(callbackDto.getTradeStatus()),"订单交易失败!");
        /**
         * 本平台业务处理
         *  查询订单记录
         *   判断订单是否存在
         *   判断订单支付状态
         *   订单记录更新
         *   账户金额更新
         *   添加充值日志记录
         */
        BusAccountRecharge busAccountRecharge=accountRechargeDao.
                queryBusAccountRechargeByOrderNo(callbackDto.getOutOrderNo(),callbackDto.getUserId());
        AssertUtil.isTrue(null==busAccountRecharge,"订单记录不存在!");
        AssertUtil.isTrue(!busAccountRecharge.getStatus().equals(2),"订单状态异常!");
        busAccountRecharge.setStatus(1);
        BigDecimal amount=BigDecimal.valueOf(Double.parseDouble(callbackDto.getTotalFee()));// 充值金额
        busAccountRecharge.setActualAmount(amount);
        busAccountRecharge.setAuditTime(new Date());
        AssertUtil.isTrue(accountRechargeDao.update(busAccountRecharge)<1,P2pConstant.OP_FAILED_MSG);
        BusAccountLog busAccountLog=new BusAccountLog();
        busAccountLog.setRepay(BigDecimal.ZERO);
        busAccountLog.setRemark("用户充值");
        busAccountLog.setTotal(amount);
        busAccountLog.setOperType("user_recharge_success");
        busAccountLog.setWait(BigDecimal.ZERO);
        busAccountLog.setFrozen(BigDecimal.ZERO);
        busAccountLog.setCash(amount);
        busAccountLog.setUsable(amount);
        busAccountLog.setOperMoney(amount);
        busAccountLog.setAddip(IpUtils.get());
        busAccountLog.setUserId(callbackDto.getUserId());
        busAccountLog.setBudgetType(1);
        busAccountLog.setAddtime(new Date());

        BusAccount busAccount=busAccountDao.queryBusAccountByUserId(callbackDto.getUserId());
        AssertUtil.isTrue(null==busAccount,P2pConstant.OP_FAILED_MSG);
        int result= busAccountDao.updateBusAccount(busAccountLog.getUsable(),busAccountLog.getFrozen(),busAccountLog.getWait(),
                busAccountLog.getCash(),busAccountLog.getRepay(),callbackDto.getUserId());
        AssertUtil.isTrue(result<1,P2pConstant.OP_FAILED_MSG);

        // 重新修改日志金额数据
        busAccountLog.setUsable(busAccountLog.getUsable().add(busAccount.getUsable()));
        busAccountLog.setFrozen(busAccountLog.getFrozen().add(busAccount.getFrozen()));
        busAccountLog.setWait(busAccountLog.getWait().add(busAccount.getWait()));
        busAccountLog.setCash(busAccountLog.getCash().add(busAccount.getCash()));
        busAccountLog.setRepay(busAccountLog.getRepay().add(busAccount.getRepay()));
        busAccountLog.setTotal(busAccountLog.getUsable()
                .add(busAccountLog.getFrozen())
                .add(busAccountLog.getWait()));
        AssertUtil.isTrue(busAccountLogDao.insert(busAccountLog)<1,P2pConstant.OP_FAILED_MSG);
    }

    @Override
    public PageList queryBusAccountRechargesByParams(BusAccountRechargeQuery busAccountRechargeQuery) {
        PageHelper.startPage(busAccountRechargeQuery.getPageNum(),busAccountRechargeQuery.getPageSize());
        List<BusAccountRecharge> busAccountRecharges=accountRechargeDao.queryBusAccountRechargesByParams(busAccountRechargeQuery);
        return new PageList(busAccountRecharges);
    }

    /**
     * 构建支付请求参数
     * @param orderNo
     * @param amount
     * @param subject
     * @param body
     * @return
     */
    private PayDto buildPayInfo(String orderNo, BigDecimal amount, String subject , String body) {
        PayDto payDto=new PayDto();
        payDto.setGatewayNew(P2pConstant.GATEWAY_NEW);
        payDto.setUserSeller(P2pConstant.USER_SELLER);
        payDto.setTotalFee(amount.toString());
        payDto.setSubject(subject);
        payDto.setReturnUrl(P2pConstant.RETURN_URL);
        payDto.setPartner(P2pConstant.PARTNER);
        payDto.setOutOrderNo(orderNo);
        payDto.setNotifyUrl(P2pConstant.NOTIFY_URL);
        payDto.setBody(body);
        String sign=buildMd5Sign(payDto);
        payDto.setSign(sign);
        return payDto;
    }

    private String buildMd5Sign(PayDto payDto) {
        StringBuffer arg = new StringBuffer();
        arg.append("body="+payDto.getBody()+"&");
        arg.append("notify_url="+payDto.getNotifyUrl()+"&");
        arg.append("out_order_no="+payDto.getOutOrderNo()+"&");
        arg.append("partner="+payDto.getPartner()+"&");
        arg.append("return_url="+payDto.getReturnUrl()+"&");
        arg.append("subject="+payDto.getSubject()+"&");
        arg.append("total_fee="+payDto.getTotalFee()+"&");
        arg.append("user_seller="+payDto.getUserSeller());
        // 如果存在转义字符，那么去掉转义
        return StringEscapeUtils.unescapeJava(arg.toString());
    }

    /**
     * 参数校验
     * @param amount
     * @param password
     * @param userId
     */
    private void checkAccountRechargeParams(BigDecimal amount, String password, Integer userId) {
         AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO)<=0,"充值金额非法!");
         AssertUtil.isTrue(null==userId||null==basUserService.queryBasUserByUserId(userId),"用户非法!");
        AssertUtil.isTrue(StringUtils.isBlank(password),"交易密码不能为空！");
        BasUserSecurity basUserSecurity= securityService.queryBasUserSecurityByUserId(userId);
        password= MD5.toMD5(password);
        AssertUtil.isTrue(!password.equals(basUserSecurity.getPaymentPassword()),"交易密码不正确!");
    }


}
