package com.shsxt.xm.service.impl;

import com.shsxt.xm.db.dao.BusAccountDao;
import com.shsxt.xm.dto.BusAccountDto;
import com.shsxt.xm.po.BusAccount;
import com.shsxt.xm.service.IBusAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/11/11.
 */
@Service
public class BusAccountServiceImpl implements IBusAccountService {
    @Resource
    private BusAccountDao busAccountDao;
    @Override
    public BusAccount queryBusAccount(Integer userId) {
        return  busAccountDao.queryBusAccountByUserId(userId);
    }

    @Override
    public BusAccountDto queryBusAccountInfoByUserId(Integer userId) {
        return busAccountDao.queryBusAccountInfoByUserId(userId);
    }
}
