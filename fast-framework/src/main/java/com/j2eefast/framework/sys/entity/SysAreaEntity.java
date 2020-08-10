package com.j2eefast.framework.sys.entity;

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

/**
 * sys_area
 * @author: ZhouZhou
 * @date 2020-06-04 23:52
 */
@Data
@TableName("sys_area")
public class SysAreaEntity implements Serializable{

   private static final long serialVersionUID = 1L;
	
  /** 区域ID */
 
 
	@TableId(type = IdType.AUTO)
    private Long id;

  /** 上级区域ID */
    private Long parentId;

  /** 名称 */
    private String name;

  /** 行政区域等级 1-省 2-市 3-区县 4-街道镇 */
    private Integer level;


  /** 完整名称 */
    private String wholeName;

  /** 本区域经度 */
    private String lon;

  /** 本区域维度 */
    private String lat;

  /** 电话区号 */
    private String cityCode;

  /** 邮政编码 */
    private String zipCode;

  /** 行政区划代码 */
    private String areaCode;

  /** 名称全拼 */
    private String pinYin;

  /** 首字母简拼 */
    private String simplePy;

  /** 区域名称拼音的第一个字母 */
  private String perPinYin;

  /**是否有节点*/
  @TableField(exist = false)
  private Integer nodes;
}
