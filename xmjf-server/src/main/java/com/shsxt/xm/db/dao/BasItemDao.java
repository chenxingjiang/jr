package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.dto.BasItemDto;
import com.shsxt.xm.po.BasItem;
import com.shsxt.xm.query.BasItemQuery;

import java.util.List;

public interface BasItemDao extends BaseDao<BasItem> {

    List<BasItemDto> queryBasItemsByParams(BasItemQuery basItemQuery);

    Integer updateBasItemStatusToOpen(Integer itemId);

}