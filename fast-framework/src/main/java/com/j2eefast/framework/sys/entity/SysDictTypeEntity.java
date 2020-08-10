package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;


/**
 * 字典表
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Date: 2019/12/18 14:38
 * @Version: 1.0
 */
@Data
@TableName("sys_dict_type")
public class SysDictTypeEntity extends BaseEntity {

    /** 字典主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 是否系统内置*/
    private String isSys;

    /** 状态（0正常 1停用） */
    private String status;
}
