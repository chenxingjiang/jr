package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BusUserStat;
import com.shsxt.xm.po.BusUserStatKey;
import org.apache.ibatis.annotations.Param;

public interface BusUserStatDao extends BaseDao<BusUserStat> {


    public  BusUserStat queryBusUserStatByUserId(@Param("userId")Integer userId);


}