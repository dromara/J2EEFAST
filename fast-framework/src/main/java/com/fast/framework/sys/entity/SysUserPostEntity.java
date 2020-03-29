package com.fast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 *
 * @ClassName: SysUserPostEntity
 * @Package: com.fast.framework.sys.entity
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/3/2 11:14
 * @version V1.0
 
 *
 */
@TableName("sys_user_post")
public class SysUserPostEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long  userId;

    private String postCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
