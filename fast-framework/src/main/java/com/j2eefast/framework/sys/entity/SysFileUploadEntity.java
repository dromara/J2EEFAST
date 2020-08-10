package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.j2eefast.common.core.base.entity.BaseEntity;

/**
 * sys_file_upload
 * @author: ZhouZhou
 * @date 2020-07-29 18:06
 */
@Data
@TableName("sys_file_upload")
public class SysFileUploadEntity extends BaseEntity{

   private static final long serialVersionUID = 1L;
	
      /** 主键 */
 
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

      /** 文件ID */
    private Long fileId;

      /** 文件名称 */
    private String fileName;

      /** 文件类型 0 文件 1图片 2其他 */
    private String fileType;

      /** 业务主键 */
    private Long bizId;

      /** 业务类型 */
    private String bizType;

  
      /** 删除标志 */
    @TableLogic(value="0",delval="1")
    private String delFlag;
  

}
