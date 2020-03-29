package com.fast.system.monitor.entity;

import cn.hutool.core.util.NumberUtil;

/**
 * 
 * @Description:CPU相关信息
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-04-03 10:09
 * @version V1.0 
 的
 *
 */
public class CpuEntity {
	/**
       * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;
    
    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public double getTotal()
    {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getSys()
    {
        return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2).doubleValue();
    }

    public void setSys(double sys)
    {
        this.sys = sys;
    }

    public double getUsed()
    {
        return NumberUtil.round(NumberUtil.mul(used / total, 100), 2).doubleValue();
    }

    public void setUsed(double used)
    {
        this.used = used;
    }

    public double getWait()
    {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
    }

    public void setWait(double wait)
    {
        this.wait = wait;
    }

    public double getFree()
    {
        return NumberUtil.round(NumberUtil.mul(free / total, 100), 2).doubleValue();
    }

    public void setFree(double free)
    {
        this.free = free;
    }
}
