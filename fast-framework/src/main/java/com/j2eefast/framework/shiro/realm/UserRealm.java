package com.j2eefast.framework.shiro.realm;

import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.framework.sys.constant.factory.ConstantFactory;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.mapper.SysMenuMapper;
import com.j2eefast.framework.sys.mapper.SysModuleMapper;
import com.j2eefast.framework.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.shiro.service.SysLoginService;
import com.j2eefast.framework.utils.Constant;


/**
 * 自定义的指定Shiro验证用户登录的类 认证
 * @author zhouzhou
 * @date 2020-03-12 20:57
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SysLoginService sysLoginService;
	@Autowired
	private SysModuleMapper sysModuleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;


	/**
	 * 授权(验证权限时调用)
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
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = "";
		if (ToolUtil.isNotEmpty(token.getPassword())){
			password = new String(token.getPassword());
        }
		// 查询用户信息
		LoginUserEntity user = new LoginUserEntity();
		try {
			user = sysLoginService.loginVerify(username, password);
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
				ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(UserUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(UserUtils.hashIterations);
		shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		super.setCredentialsMatcher(shaCredentialsMatcher);
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
//			List<String> datalist = new ArrayList<>();
//			int dataScope = -1;
//			for (Long roleId : roleList) {
//				SysRoleEntity role = ConstantFactory.me().getRoleById(roleId);
//				int temp = Integer.parseInt(role.getDataScope());
//				if(temp == 5){
//					datalist.add(String.valueOf(temp));
//				}else{
//					if(dataScope < temp){
//						dataScope = temp;
//					}
//				}
//				roleNameList.add(role.getRoleName());
//				roleKeyList.add(role.getRoleKey());
//			}
//			if(dataScope == 6){
//				datalist.clear();
//			}
//			loginUser.setRoleList(roleList);
//			loginUser.setRoleNames(roleNameList);
//			loginUser.setRoleKey(roleKeyList);

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
