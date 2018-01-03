package com.shsxt.xm.service.impl;

import com.shsxt.xm.db.dao.BusItemLoanDao;
import com.shsxt.xm.po.BusItemLoan;
import com.shsxt.xm.service.IBusItemLoanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/11/11.
 */
@Service
public class BusItemLoanServiceImpl implements IBusItemLoanService {
    @Resource
    private BusItemLoanDao busItemLoanDao;
    @Override
    public BusItemLoan queryBusItemLoanByItemId(Integer itemId) {
        return busItemLoanDao.queryBusItemLoanByItemId(itemId);
    }
}
