package com.shsxt.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.BasItemDao;
import com.shsxt.xm.dto.BasItemDto;
import com.shsxt.xm.po.BasItem;
import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.IBasItemService;
import com.shsxt.xm.utils.AssertUtil;
import com.shsxt.xm.utils.PageList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lp on 2017/11/9.
 */
@Service
public class BasItemServiceImpl implements IBasItemService {
    @Resource
    private BasItemDao basItemDao;

    @Override
public PageList queryBasItemsByParams(BasItemQuery basItemQuery) {
    PageHelper.startPage(basItemQuery.getPageNum(),basItemQuery.getPageSize());
    List<BasItemDto> basItemDtos=basItemDao.queryBasItemsByParams(basItemQuery);
    if(null!=basItemDtos&&basItemDtos.size()>0){
        for(BasItemDto basItemDto:basItemDtos){
            if(basItemDto.getItemStatus().equals(1)){
                Date relaseTime=basItemDto.getReleaseTime();
                Date currentTime=new Date();
                Long syTime=relaseTime.getTime()-currentTime.getTime();
                if(syTime>0){
                    basItemDto.setSyTime(syTime/1000);
                }
            }
        }
    }
    PageList pageList=new PageList(basItemDtos);
     return pageList;
}

    @Override
    public void updateBasItemStatusToOpen(Integer itemId) {
        AssertUtil.isTrue(null==itemId||null==basItemDao.queryById(itemId),"待开放的项目不存在!");
        AssertUtil.isTrue(basItemDao.updateBasItemStatusToOpen(itemId)<1, P2pConstant.OP_FAILED_MSG);
    }

    @Override
    public BasItem queryBasItemById(Integer itemId) {
        return basItemDao.queryById(itemId);
    }
}
