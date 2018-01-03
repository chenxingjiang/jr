package com.shsxt.xm.service;

import com.shsxt.xm.po.SysPicture;

import java.util.List;

/**
 * Created by lp on 2017/11/11.
 */
public interface ISysPictureService {
    public List<SysPicture> querySysPicturesByItemId(Integer itemId);
}
