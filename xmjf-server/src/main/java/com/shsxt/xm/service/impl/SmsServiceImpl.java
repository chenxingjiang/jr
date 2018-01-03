package com.shsxt.xm.service.impl;

import com.alibaba.fastjson.JSON;
import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.BasUserDao;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.service.ISmsService;
import com.shsxt.xm.utils.AssertUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lp on 2017/11/7.
 */
@Service
public class SmsServiceImpl  implements ISmsService {
    @Resource
    private IBasUserService basUserService;
    @Override
    public void sendPhoneCode(String phone, String code) throws  Exception{
        /**
         * 1.参数合法性校验
         *    phone 不空
         *    code 非空
         * 2.手机号唯一性校验
         * 3.执行短信发送行为
         * 1111111111
         *   13
         *   15
         *   17
         *   18
         *
         */
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(code),"验证码不能为空!");
        AssertUtil.isTrue(null!=basUserService.queryBasUserByPhone(phone),"该手机号已注册!");
        TaobaoClient client = new DefaultTaobaoClient(P2pConstant.TAOBAO_SEND_PHONE_URL
                ,P2pConstant.TAOBAO_APP_KEY, P2pConstant.TAOBAO_APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType(P2pConstant.TAOBAO_SMS_TYPE);
        req.setSmsFreeSignName(P2pConstant.TAOBAO_SMS_FREE_SIGN_NAME);
        Map<String,String> map=new HashMap<String,String>();
        map.put("code",code);
        req.setSmsParamString(JSON.toJSONString(map));
        req.setRecNum(phone );
        req.setSmsTemplateCode(P2pConstant.TAOBAO_SMS_TEMPLATE_CODE);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        client.execute(req);
    }

}
