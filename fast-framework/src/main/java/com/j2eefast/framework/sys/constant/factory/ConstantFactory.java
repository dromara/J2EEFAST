package com.j2eefast.framework.sys.constant.factory;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 10:04
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.constants.Cache;
import com.j2eefast.common.core.constants.CacheKey;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.log.entity.SysLoginInfoEntity;
import com.j2eefast.framework.log.mapper.SysLoginInfoMapper;
import com.j2eefast.framework.sys.entity.SysMenuEntity;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.mapper.SysRoleMapper;
import com.j2eefast.framework.sys.mapper.SysUserMapper;
import com.j2eefast.framework.sys.service.SysMenuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>获取方法</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-02 13:13
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Component
@DependsOn("springUtil")
public class ConstantFactory implements IConstantFactory {

	private SysRoleMapper sysRoleMapper = SpringUtil.getBean(SysRoleMapper.class);
	private SysUserMapper sysUserMapper = SpringUtil.getBean(SysUserMapper.class);
	private SysMenuService sysMenuService = SpringUtil.getBean(SysMenuService.class);
	private SysLoginInfoMapper sysLoginInfoMapper = SpringUtil.getBean(SysLoginInfoMapper.class);

	public static IConstantFactory me() {
		return SpringUtil.getBean("constantFactory");
	}


	@Override
	public String getUserNameById(Long userId) {
		SysUserEntity sysUser = sysUserMapper.selectById(userId);
		if (ToolUtil.isNotEmpty(sysUser)) {
			return sysUser.getName();
		} else {
			return "--";
		}
	}

	@Override
	public String getUserUserNameById(Long userId) {
		SysUserEntity sysUser = sysUserMapper.selectById(userId);
		if (ToolUtil.isNotEmpty(sysUser)) {
			return sysUser.getUsername();
		} else {
			return "--";
		}
	}



	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#userId")
	public String getRoleName(Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			return "";
		}
		List<SysRoleEntity> roleList = sysRoleMapper.getRolesByUserId(userId);
		if(ToolUtil.isEmpty(roleList)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		roleList.forEach(role->{
			sb.append(role.getRoleName()).append(",");
		});
		return StrUtil.removeSuffix(sb.toString(),",");
	}

	@Override
	public List<Long> getRoleIds(Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			return null;
		}
		List<SysRoleEntity>  roleList = sysRoleMapper.getRolesByUserId(userId);
		if(ToolUtil.isEmpty(roleList)) {
			return null;
		}
		List<Long> roleids = new ArrayList<>(roleList.size());
		roleList.forEach(role->{
			roleids.add(role.getRoleId());
		});
		return roleids;
	}

	@Override
	@Cacheable(value = Cache.ROLECONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
	public String getSingleRoleName(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		SysRoleEntity roleObj = sysRoleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getRoleName())) {
			return roleObj.getRoleName();
		}
		return "";
	}

	@Override
	@Cacheable(value = Cache.ROLECONSTANT, key = "'" + CacheKey.SINGLE_ROLE_KEY + "'+#roleId")
	public String getSingleRoleKey(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		SysRoleEntity roleObj = sysRoleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getRoleKey())) {
			return roleObj.getRoleKey();
		}
		return "";
	}

	@Override
	public String getDeptName(Long deptId) {
		return null;
	}

	@Override
	public String getStatusName(String status) {
		return null;
	}

	@Override
	public String getMenuStatusName(String status) {
		return null;
	}

	@Override
	@Cacheable(value = Cache.MENU_CONSTANT, key = "'" + CacheKey.MENU_NAME + "'+ T(String).valueOf(#userId).concat('-').concat(#moduleCode)")
	public List<SysMenuEntity> getMenuByUserIdModuleCode(Long userId, String moduleCode) {
		List<SysMenuEntity> menuList = sysMenuService.findUserModuleMenuList(userId,moduleCode);
		if(ToolUtil.isNotEmpty(menuList)){
			return menuList;
		}
		return null;
	}



	@Override
	public String getCacheObject(String para) {
		return null;
	}

	@Override
	public List<Long> getSubDeptId(Long deptId) {
		return null;
	}

	@Override
	public List<Long> getParentDeptIds(Long deptId) {
		return null;
	}

	@Override
	public String getPositionName(Long userId) {
		return null;
	}

	@Override
	public String getPositionIds(Long userId) {
		return null;
	}

	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.COMP_NAME + "'+#userId")
	public String getCompName(Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			return "";
		}
		String compName = sysUserMapper.findCompNameByUserId(userId);
		if (ToolUtil.isNotEmpty(compName)) {
			return compName;
		}
		return "";
	}

	@Override
	@CacheEvict(value=Cache.MENU_CONSTANT, allEntries=true)
	public void clearMenu() {
	}

	@Override
	@CacheEvict(value=Cache.ROLECONSTANT, allEntries=true)
	public void clearRole() {
	}


	@Override
	public SysLoginInfoEntity getFirstLoginInfo(String username) {
		SysLoginInfoEntity loginInfo = sysLoginInfoMapper.findFirstLoginInfo(username);
		if(ToolUtil.isEmpty(loginInfo)){
			loginInfo = new SysLoginInfoEntity();
			loginInfo.setLoginLocation("首次登陆");
			loginInfo.setLoginTime(DateUtil.date());
		}
		return loginInfo;
	}
}
