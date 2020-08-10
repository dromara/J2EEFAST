package com.j2eefast.generator.gen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.j2eefast.common.core.base.entity.BaseEntity;
import java.util.Date;

/**
 * example_test
 * @author: ZhouZhou
 * @date 2020-08-07 11:33
 */
@Data
@TableName("example_test")
public class ExampleTestEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /** id */ 
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /** 员工编号 */
    @NotBlank(message = "参数值不能为空")
    private String code;

    /** 姓名 */
    @NotBlank(message = "参数值不能为空")
    private String name;

    /** 邮箱 */
    private String email;

    /** 头像 */
    private String avatar;

    /** 电话 */
    @NotBlank(message = "参数值不能为空")
    private String phone;

    /** 性别 */
    private String sex;

    /** 年龄 */
    @NotNull(message = "参数值不能为空")
    private Long age;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date joinDate;

    /** 离职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date leaveDate;

    /** 归属公司 */
    private Long compId;
    @TableField(exist=false)
    private String compName;

    /** 归属部门 */
    private Long deptId;
    @TableField(exist=false)
    private String deptName;

    /** 地址 */
    private String addr;

    /** 地址详情 */
    private String addrinfo;

    /** 删除标记（0：正常；1：删除） */
    @TableLogic(value="0",delval="1")
    private String delFlag;
}
