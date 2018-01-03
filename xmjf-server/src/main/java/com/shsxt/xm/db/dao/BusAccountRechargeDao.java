package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BusAccountRecharge;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusAccountRechargeDao extends BaseDao<BusAccountRecharge> {

    /**
     * 根据订单查询订单记录
     * @param orderNo
     * @return
     */
    public  BusAccountRecharge queryBusAccountRechargeByOrderNo(@Param("orderNo") String orderNo,@Param("userId") Integer userId);


    public List<BusAccountRecharge> queryBusAccountRechargesByParams(BusAccountRechargeQuery busAccountRechargeQuery);

}