package com.fast.system.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @ClassName: 报信息
 * @Package: com.fast.system.poi
 * @Description: sys_poi
 * @author: ZhouHuan
 * @time 2020-01-08
 
 *
 */
@TableName("sys_poi")
public class SysPoicEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** ID */
 
 
    @TableId
    private Long id;
    /** 公司ID */
    private Long compId;
    /** 公司名称 */
    private String name;
    /** 报表时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date time;
    /** 报表文件名称 */
    private String filename;
    /** 报表文件路径 */
    private String path;
    /** 报表类型 */
    private Integer type;
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCompId(Long compId) 
    {
        this.compId = compId;
    }

    public Long getCompId() 
    {
        return compId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setFilename(String filename) 
    {
        this.filename = filename;
    }

    public String getFilename() 
    {
        return filename;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }

}
