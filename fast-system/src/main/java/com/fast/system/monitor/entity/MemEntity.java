package com.fast.system.monitor.entity;

import cn.hutool.core.util.NumberUtil;

/**
 * 
 * @Description: 內存相关信息
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-04-03 10:26
 * @version V1.0 
 的
 *
 */
public class MemEntity
{
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal()
    {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public double getUsed()
    {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public void setUsed(long used)
    {
        this.used = used;
    }

    public double getFree()
    {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(long free)
    {
        this.free = free;
    }

    public double getUsage()
    {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}
