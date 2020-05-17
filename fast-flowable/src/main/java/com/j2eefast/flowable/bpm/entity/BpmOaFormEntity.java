package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.j2eefast.common.core.base.entity.BaseEntity;
import java.util.Date;

/**
 * bpm_oa_form
 * @author: ZhouZhou
 * @date 2020-04-20 22:19
 */
@Data
@TableName("bpm_oa_form")
public class BpmOaFormEntity extends BaseEntity{

    /** 主键 */
 
    @TableId(value = "id",type = IdType.INPUT)
    private String id;


    /**流程实例ID*/
    private String processInstanceId;

    /** 请假人ID */
    private String userId;

    /** 请假人姓名 */
    private String userName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")

    @NotNull(message = "参数值不能为空")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")

    @NotNull(message = "参数值不能为空")
    private Date endTime;

    /** 请假天数 */

    @NotNull(message = "参数值不能为空")
    private Integer leaveDays;

    /** 请假类型 */
    private String leaveType;

    /** 请假原因 */

    @NotBlank(message = "参数值不能为空")
    private String leaveReason;

    /** 状态 */
    private String status;

    /** 是否删除 */
    @TableLogic
    private String delFlag;


    @TableField(exist = false)
    private String fromName;
    @TableField(exist = false)
    private String modelKey;

}
