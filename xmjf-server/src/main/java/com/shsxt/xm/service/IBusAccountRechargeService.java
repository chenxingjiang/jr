package com.shsxt.xm.service;

import com.shsxt.xm.dto.CallbackDto;
import com.shsxt.xm.dto.PayDto;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import com.shsxt.xm.utils.PageList;

import java.math.BigDecimal;

/**
 * Created by lp on 2017/11/14.
 */
public interface IBusAccountRechargeService  {
    public PayDto addBusAccountRechagre(BigDecimal amount, String password, Integer userId);

    public void updateBusAccountRechargeCallBack(CallbackDto callbackDto);

    public PageList queryBusAccountRechargesByParams(BusAccountRechargeQuery busAccountRechargeQuery);
}
