package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>系统公告</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-26 10:48
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@TableName("sys_notice")
@Data
public class SysNoticeEntity  extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告提示栏
     */
    private String noticeTipTitle;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date endTime;

    /**
     * 状态 0 发布 1未发表 2失效
     */
    private String status;

    /**
     *  静态页编号
     */
    private String htmlNo;

    /**
     *  公告类型 0通知 1公告
     */
    private String noticeType;

    /**
     * 公告通知 等级 0 普通 1紧急 2严重
     */
    private String noticeLevel;

    /**
     * 公告内容
     */
    @TableField(exist = false)
    private String noticeContent;
}
