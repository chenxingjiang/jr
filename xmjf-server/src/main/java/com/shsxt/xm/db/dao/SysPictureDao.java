package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.SysPicture;

import java.util.List;

public interface SysPictureDao extends BaseDao<SysPicture> {

    public List<SysPicture>  querySysPicturesByItemId(Integer itemId);
}