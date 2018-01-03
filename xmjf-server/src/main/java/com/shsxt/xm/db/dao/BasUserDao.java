package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.po.BasUserInfo;
import org.apache.ibatis.annotations.Param;

public interface BasUserDao extends BaseDao<BasUser> {
    public  BasUser queryBasUserByPhone(@Param("phone")String phone);




}