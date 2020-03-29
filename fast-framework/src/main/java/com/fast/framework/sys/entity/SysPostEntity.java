package com.fast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;


/**

 * @ClassName: 岗位信息
 * @Package: com.fast.framework.sys
 * @Description: sys_post
 * @author: ZhouHuan
 * @time 2020-02-28
 
 * /----------------------------/
 * /---><---/
 * /----------------------------/
 */
@TableName("sys_post")
public class SysPostEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
 
 
    @TableId
    private Long postId;

    /** 岗位编码 */

    @NotBlank(message = "参数值不能为空")
    private String postCode;

    /** 岗位名称 */

    @NotBlank(message = "参数值不能为空")
    private String postName;

    /** 显示顺序 */

    @NotNull(message = "参数值不能为空")
    private Integer postSort;

    /** 岗位分类 */

    @NotBlank(message = "参数值不能为空")
    private String postType;

    /** 状态（0正常 1停用） */

    @NotBlank(message = "参数值不能为空")
    private String status;

    /** 用户是否存在此标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }
    public Long getPostId()
    {
        return postId;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }
    public String getPostCode()
    {
        return postCode;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }
    public String getPostName()
    {
        return postName;
    }

    public void setPostSort(Integer postSort)
    {
        this.postSort = postSort;
    }
    public Integer getPostSort()
    {
        return postSort;
    }

    public void setPostType(String postType)
    {
        this.postType = postType;
    }
    public String getPostType()
    {
        return postType;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getStatus()
    {
        return status;
    }







}
