package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**

 * @ClassName: 岗位信息
 * @Description: sys_post
 * @author: zhouzhou
 * @time 2020-02-28
 */
@Data
@TableName("sys_post")
public class SysPostEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    @TableId(type = IdType.ASSIGN_ID)
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


}
