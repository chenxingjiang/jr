package com.shsxt.xm.dto;

/**
 * Created by lp on 2017/11/14.
 */
public class PayDto {

    // 商户订单号
    private String outOrderNo;
    // 订单名称
    private String subject;
    // 订单金额
    private String totalFee;
    // 订单描述信息
    private String body;
    // 异步通知url
    private String notifyUrl;
    // 同步url
    private String returnUrl;
    // 用户商户号
    private String userSeller;
    // pid
    private String partner;
    // 支付请求签名串
    private String sign;
    //  支付地址
    private String gatewayNew;

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUserSeller() {
        return userSeller;
    }

    public void setUserSeller(String userSeller) {
        this.userSeller = userSeller;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getGatewayNew() {
        return gatewayNew;
    }

    public void setGatewayNew(String gatewayNew) {
        this.gatewayNew = gatewayNew;
    }
}
