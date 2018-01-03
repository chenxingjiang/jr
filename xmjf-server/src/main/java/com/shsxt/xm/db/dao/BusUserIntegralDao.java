package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BasUserInfo;
import com.shsxt.xm.po.BusUserIntegral;
import org.apache.ibatis.annotations.Param;

public interface BusUserIntegralDao extends BaseDao<BusUserIntegral>{


    public  BusUserIntegral queryBusUserInteGralByUserId(@Param("userId")Integer userId);
    //public  Integer updateUserInteGral(@Param("userId")Integer userId,@Param("jf")Integer jf);

}