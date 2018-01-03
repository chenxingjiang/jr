package com.shsxt.xm.service;

import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.utils.PageList;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lp on 2017/11/11.
 */
public interface IBusItemInvestService {

    public PageList queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery);

    public  void addBusItemInvest(BigDecimal amount, Integer itemId, String password,Integer userId);

    public Map<String,Object> queryItemInvestsFiveMonthByUserId(Integer userId);
}
