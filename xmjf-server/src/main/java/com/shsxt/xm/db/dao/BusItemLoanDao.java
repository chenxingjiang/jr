package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BusItemLoan;

public interface BusItemLoanDao extends BaseDao<BusItemLoan> {
    public  BusItemLoan queryBusItemLoanByItemId(Integer itemId);

}