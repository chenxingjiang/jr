package com.shsxt.xm.service;

import com.shsxt.xm.po.BusItemLoan;

/**
 * Created by lp on 2017/11/11.
 */
public interface IBusItemLoanService {

    public BusItemLoan queryBusItemLoanByItemId(Integer itemId);
}
