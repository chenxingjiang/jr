package com.shsxt.xm.service.impl;

import com.shsxt.xm.db.dao.SysPictureDao;
import com.shsxt.xm.po.SysPicture;
import com.shsxt.xm.service.ISysPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lp on 2017/11/11.
 */
@Service
public class SysPictureServiceImpl implements ISysPictureService {
    @Resource
    private SysPictureDao sysPictureDao;
    @Override
    public List<SysPicture> querySysPicturesByItemId(Integer itemId) {
        return sysPictureDao.querySysPicturesByItemId(itemId);
    }
}
