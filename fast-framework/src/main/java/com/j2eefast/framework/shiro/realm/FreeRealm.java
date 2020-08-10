package com.j2eefast.framework.shiro.realm;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.shiro.service.SysLoginService;
import com.j2eefast.framework.sys.constant.factory.ConstantFactory;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.mapper.SysMenuMapper;
import com.j2eefast.framework.sys.mapper.SysModuleMapper;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 第三方账号免密登录 指定Shiro验证用户登录的类 认证
 */
@Slf4j
public class FreeRealm extends AuthorizingRealm {


    @Autowired
    private SysModuleMapper sysModuleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 免密授权授权认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUserEntity user = (LoginUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 系统管理员，拥有最高权限
        if (userId.equals(Constant.SUPER_ADMIN) || user.getRoleKey().contains(Constant.SU_ADMIN)){
            info.addRole("ADMIN");
            info.addStringPermission("*:*:*");
        } else {
            info.addRoles(user.getRoleKey());
            info.setStringPermissions(user.getPermissions());
        }
        return info;
    }


    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        // 查询用户信息
        LoginUserEntity user = new LoginUserEntity();
        try {
            user = this.sysLoginService.freeLoginVerify(username);
        }catch (RxcException e) {
            //不同异常不同抛出
            if(e.getCode().equals("50001")) {
                throw new UnknownAccountException(e.getMessage(), e);
            }else if(e.getCode().equals("50002")) {
                throw new LockedAccountException(e.getMessage(), e);
            }else if(e.getCode().equals("50003")) {
                throw new ExcessiveAttemptsException(e.getMessage(), e);
            }else if(e.getCode().equals("50005")) {
                throw new IncorrectCredentialsException(e.getMessage(), e);
            }else if(e.getCode().equals("50004")) {
                throw new UnknownAccountException(e.getMessage(), e);
            }
        }catch (Exception e){
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
                null, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {

        LoginUserEntity loginUser = UserUtils.getUserInfo();

        //清理缓存
        ConstantFactory.me().clearMenu();
        ConstantFactory.me().clearRole();
        if(!loginUser.getId().equals(Constant.SUPER_ADMIN) || !loginUser.getRoleKey().contains(Constant.SU_ADMIN)){

            //获取用户角色列表
            List<Long> roleList = ConstantFactory.me().getRoleIds(loginUser.getId());
            List<String> roleNameList = new ArrayList<>();
            List<String> roleKeyList = new ArrayList<>();
            // 根居角色ID获取模块列表
            List<SysModuleEntity> modules = this.sysModuleMapper.findModuleByRoleIds(roleList);
            List<Map<String, Object>>  results = new ArrayList<>(modules.size());
            modules.forEach(module->{
                Map<String, Object> map = BeanUtil.beanToMap(module);
                results.add(map);
            });
            loginUser.setModules(results);
            //设置权限列表
            Set<String> permissionSet = new HashSet<>();
            List<Map<Object,Object>> xzz = new ArrayList<>(roleList.size());
            for (Long roleId : roleList) {
                SysRoleEntity role = ConstantFactory.me().getRoleById(roleId);
                List<String> permissions = this.sysMenuMapper.findPermsByRoleId(roleId);
                if (permissions != null) {
                    Map<Object, Object> map = new HashMap<>();
                    Set<String> tempSet = new HashSet<>();
                    for (String permission : permissions) {
                        if (ToolUtil.isNotEmpty(permission)) {
                            String[] perm = StrUtil.split(permission,",");
                            for(String s: perm){
                                permissionSet.add(s);
                                tempSet.add(s);
                            }
                        }
                    }
                    map.put(role,tempSet);
                    xzz.add(map);
                }
                roleNameList.add(role.getRoleName());
                roleKeyList.add(role.getRoleKey());
            }
            loginUser.setRoleList(roleList);
            loginUser.setRoleNames(roleNameList);
            loginUser.setRoleKey(roleKeyList);
            loginUser.setRolePerm(xzz);
            loginUser.setPermissions(permissionSet);

            //刷新用户
            UserUtils.reloadUser(loginUser);
        }
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
