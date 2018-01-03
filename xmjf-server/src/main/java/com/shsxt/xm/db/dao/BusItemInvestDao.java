package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.dto.BusItemInvestDto;
import com.shsxt.xm.po.BusItemInvest;
import com.shsxt.xm.query.BusItemInvestQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusItemInvestDao extends BaseDao<BusItemInvest> {

    public List<BusItemInvestDto> queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery);


    public  List<BusItemInvestDto>  queryItemInvestsFiveMonthByUserId(@Param("userId") Integer userId);

}