package com.j2eefast.framework.utils;


import com.j2eefast.framework.sys.entity.SysUserEntity;

import cn.hutool.core.util.StrUtil;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: fast
 * @Package: com.j2eefast.framework.utils
 * @ClassName: PermissionConfig
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Description: 首创 html调用 Freemarker 实现按钮权限可见性
 * @Date: 2019/12/19 16:48
 * @Version: 1.0
 */
@Component
public class PermissionConfig {
    /** 没有权限，hidden用于前端隐藏按钮 */
    public static final String NOACCESS = "hidden";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 验证用户是否具备某权限，无权限返回hidden用于前端隐藏（如需返回Boolean使用isPermitted）
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public String hasPermi(String permission)
    {
        return isPermitted(permission) ? StrUtil.EMPTY : NOACCESS;
    }

    /**
     * 判断用户是否拥有某个权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean isPermitted(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * 判断用户是否拥有多个权限
     * @param permission
     * @return
     */
    public boolean isPermitteds(String permission)
    {
        boolean isFlag = false;
        String[] permissions =  permission.split(",");
        for(String p : permissions){
            if(SecurityUtils.getSubject().isPermitted(p)){
                isFlag = true;
                break;
            }
        }
        return  isFlag;
    }

    /**
     * 判断用户是否具有某个角色
     * @param permission
     * @return
     */
    public boolean isRole(String permission)
    {
        return UserUtils.hasRole(permission);
    }
    
    
    public SysUserEntity getUserEntity() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}
    

}
