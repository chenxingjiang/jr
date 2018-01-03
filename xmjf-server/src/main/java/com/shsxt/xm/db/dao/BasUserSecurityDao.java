package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BasUserSecurity;
import org.apache.ibatis.annotations.Param;

public interface BasUserSecurityDao extends BaseDao<BasUserSecurity>{
    /**
     * 查询贷款人基本信息
     * @param userId
     * @return
     */
    public  BasUserSecurity queryBasUserSecurityByUserId(Integer userId);

    public  BasUserSecurity queryBasUserSecurityByCard(@Param("card")String card);

}