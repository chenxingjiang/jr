package com.shsxt.xm.dto;

import com.shsxt.xm.po.BasItem;

/**
 * Created by lp on 2017/11/9.
 */
public class BasItemDto extends BasItem{
    private Integer total;
    private Long syTime;// 贷款项目剩余离发布剩余时间

    public Long getSyTime() {
        return syTime;
    }

    public void setSyTime(Long syTime) {
        this.syTime = syTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
