package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.dto.BusAccountDto;
import com.shsxt.xm.po.BusAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface BusAccountDao extends BaseDao<BusAccount> {

    public  BusAccount queryBusAccountByUserId(Integer userId);

    /**
     *
     * @param usableAmount  可用金额
     * @param frozenAmount  冻结金额
     * @param waitAmount    待收金额
     * @param cashAmount    可提现金额
     * @param repayAmount   待还款金额
     * @param userId         登录用户id
     * @return
     */
    public  Integer updateBusAccount(
                                  @Param("usableAmount") BigDecimal usableAmount,
                                  @Param("frozenAmount") BigDecimal frozenAmount,
                                  @Param("waitAmount") BigDecimal waitAmount,
                                  @Param("cashAmount")BigDecimal cashAmount,
                                  @Param("repayAmount") BigDecimal repayAmount,
                                  @Param("userId") Integer userId
                                 );

    //public  Integer updateBusAccountOnInvest(BigDecimal amount,BigDecimal lx,);


    public BusAccountDto queryBusAccountInfoByUserId(@Param("userId")Integer userId);


}