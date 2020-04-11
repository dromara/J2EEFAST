package com.j2eefast.system.monitor.domain;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.system.monitor.entity.CpuEntity;
import com.j2eefast.system.monitor.entity.JvmEntity;
import com.j2eefast.system.monitor.entity.MemEntity;
import com.j2eefast.system.monitor.entity.SysEntity;
import com.j2eefast.system.monitor.entity.SysFileEntity;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.SystemUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 服务器相关信息
 * 
 * @author ruoyi
 */
public class Server
{
    
    private static final int OSHI_WAIT_SECOND = 1000;
    
    /**
     * CPU相关信息
     */
    private CpuEntity cpu = new CpuEntity();

    /**
     * 內存相关信息
     */
    private MemEntity mem = new MemEntity();

    /**
     * JVM相关信息
     */
    private JvmEntity jvm = new JvmEntity();

    /**
     * 服务器相关信息
     */
    private SysEntity sys = new SysEntity();

    /**
     * 磁盘相关信息
     */
    private List<SysFileEntity> sysFiles = new LinkedList<SysFileEntity>();

    public CpuEntity getCpu()
    {
        return cpu;
    }

    public void setCpu(CpuEntity cpu)
    {
        this.cpu = cpu;
    }

    public MemEntity getMem()
    {
        return mem;
    }

    public void setMem(MemEntity mem)
    {
        this.mem = mem;
    }

    public JvmEntity getJvm()
    {
        return jvm;
    }

    public void setJvm(JvmEntity jvm)
    {
        this.jvm = jvm;
    }

    public SysEntity getSys()
    {
        return sys;
    }

    public void setSys(SysEntity sys)
    {
        this.sys = sys;
    }

    public List<SysFileEntity> getSysFiles()
    {
        return sysFiles;
    }

    public void setSysFiles(List<SysFileEntity> sysFiles)
    {
        this.sysFiles = sysFiles;
    }

    public void copyTo() throws Exception
    {
        SystemInfo si = new SystemInfo();
        
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setSysInfo();

        setJvmInfo();

        setSysFiles(si.getOperatingSystem());
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor)
    {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory)
    {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo()
    {
        sys.setComputerName(SystemUtil.getHostInfo().getName());
        sys.setComputerIp(SystemUtil.getHostInfo().getAddress());
        sys.setOsName(SystemUtil.getOsInfo().getName());
        sys.setOsArch(SystemUtil.getOsInfo().getArch());
        sys.setUserDir(SystemUtil.getUserInfo().getCurrentDir());
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() throws UnknownHostException
    {
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(SystemUtil.getJavaInfo().getVersion());
        jvm.setHome(SystemUtil.getJavaRuntimeInfo().getHomeDir());
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os)
    {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray)
        {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFileEntity sysFile = new SysFileEntity();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(ToolUtil.convertFileSize(total));
            sysFile.setFree(ToolUtil.convertFileSize(free));
            sysFile.setUsed(ToolUtil.convertFileSize(used));
            sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }
}
