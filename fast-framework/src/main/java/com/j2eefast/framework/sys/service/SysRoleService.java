package com.j2eefast.framework.sys.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.config.RabbitmqProducer;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.rabbit.constant.RabbitBeanInfo;
import com.j2eefast.common.rabbit.constant.RabbitInfo;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.entity.SysRoleModuleEntity;
import com.j2eefast.framework.sys.mapper.SysRoleMapper;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色
 * @author zhouzhou
 */
@Service
public class SysRoleService  extends ServiceImpl<SysRoleMapper, SysRoleEntity> {

	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysRoleMenuService sysRoleMenuService;
	@Resource
	private SysRoleModuleService sysRoleModuleService;
	@Autowired
	private RabbitmqProducer rabbitmqProducer;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	/**
	 * 页面展示查询翻页
	 */
	@DataFilter(deptAlias = "c")
	public PageUtil findPage(Map<String, Object> params) {
		String roleName = (String) params.get("roleName");
		String roleKey = (String) params.get("roleKey");
		Page<SysRoleEntity> page = this.baseMapper.findPage(new Query<SysRoleEntity>(params).getPage(),
																roleName,
																roleKey,
																(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

	@DataFilter(deptAlias = "c")
	public List<SysRoleEntity> getRoleList(Map<String, Object> params){
		String roleName = (String) params.get("roleName");
		String roleKey = (String) params.get("roleKey");
		return this.baseMapper.getRoleList(roleName,roleKey,(String) params.get(Constant.SQL_FILTER));
	}

	/**
	 * 获取所有角色信息
	 * @return
	 */
	public List<SysRoleEntity> getRolesAll(){
		return SpringUtil.getAopProxy(this).getRoleList(new HashMap<>(1));
	}

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SysRoleEntity role){
		if(this.save(role)){

			// 保存角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getId(), Arrays.asList(role.getMenuIds()));

			//插入角色管理 模块
			String[] ls = role.getModuleCodes().split(",");
			for(String s: ls){
				SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
				sysRoleModuleEntity.setModuleCode(s);
				sysRoleModuleEntity.setRoleId(role.getId());
				sysRoleModuleService.save(sysRoleModuleEntity);
			}

			rabbitmqProducer.sendSimpleMessage(RabbitInfo.getAddRoleHard(), JSONObject.toJSONString(role),
					IdUtil.fastSimpleUUID(), RabbitInfo.EXCHANGE_NAME, RabbitInfo.KEY);

			return true;
		}
		return false;
	}

	/**
	 * 更新
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SysRoleEntity role) {

		if(this.updateById(role)){



			// 更新角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getId(), Arrays.asList(role.getMenuIds()));

			sysRoleModuleService.deleRoleModule(role.getId());

			//插入角色管理 模块
			String[] ls = role.getModuleCodes().split(",");
			for(String s: ls){
				SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
				sysRoleModuleEntity.setModuleCode(s);
				sysRoleModuleEntity.setRoleId(role.getId());
				sysRoleModuleService.save(sysRoleModuleEntity);
			}

			rabbitmqProducer.sendSimpleMessage(RabbitInfo.getUpdateRoleHard(), JSONArray.toJSONString(role),
					IdUtil.fastSimpleUUID(), RabbitInfo.EXCHANGE_NAME, RabbitInfo.KEY);

			//清理权限缓存
			UserUtils.clearCachedAuthorizationInfo();
			return true;
		}
		return false;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatchByIds(Long[] ids) {

		// 检查用户与角色关联
		List<SysRoleEntity> list = sysRoleMapper.findRoleByIds(ids);

		if(ToolUtil.isNotEmpty(list)){
			throw new RxcException(String.format("%1$s已分配,不能删除", list.get(0).getRoleName()));
		}

		// 删除角色与菜单关联
		sysRoleMenuService.deleteBatchByRoleIds(ids);

		// 删除角色与机构关联
		sysRoleDeptService.deleteBatchByRoleIds(ids);

		// 删除角色与模块关联
		sysRoleModuleService.deleteBatchByRoleIds(ids);

		//清理权限缓存
		UserUtils.clearCachedAuthorizationInfo();

		if(this.removeByIds(Arrays.asList(ids))){
			rabbitmqProducer.sendSimpleMessage(RabbitInfo.getDelRoleHard(), ToolUtil.conversion(ids,","),
					IdUtil.fastSimpleUUID(), RabbitInfo.EXCHANGE_NAME, RabbitInfo.KEY);
			return true;
		}
		return false;
	}


	public boolean checkRoleNameUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getId())?-1L:role.getId();
		SysRoleEntity info = sysRoleMapper.checkRoleNameUnique(role.getRoleName());
		if(!ToolUtil.isEmpty(info) && !info.getId().equals(roleId)){
			return  false;
		}
		return true;
	}

	public boolean checkRoleKeyUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getId())?-1L:role.getId();
		SysRoleEntity info = sysRoleMapper.checkRoleKeyUnique(role.getRoleKey());
		if(!ToolUtil.isEmpty(info) && !info.getId().equals(roleId)){
			return  false;
		}
		return true;
	}

	public List<SysRoleEntity> selectRolesByUserId(Long userId) {
		return sysRoleMapper.getRolesByUserId(userId);
	}

	/**
	 * 通过用户id获取用户角色权限id 用逗号隔开
	 * @param userId
	 * @return
	 */
	public String getRolesByUserIdToStr(Long userId){
		List<SysRoleEntity> roles = this.selectRolesByUserId(userId);
		if(ToolUtil.isNotEmpty(roles)){
			StringBuffer sb = new StringBuffer(StrUtil.EMPTY);
			for(SysRoleEntity role: roles){
				sb.append(role.getId()).append(StrUtil.COMMA);
			}
			return  sb.substring(0,sb.length()-1);
		}else{
			return StrUtil.EMPTY;
		}
	}

	public boolean changeStatus(SysRoleEntity role) {
		return this.updateById(role);
	}

	public void checkRoleAllowed(SysRoleEntity role){
		if(role.getRoleKey().equals(Constant.SU_ADMIN)){
			new RxcException("超级管理员不允许操作!");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean authDataScope(SysRoleEntity role){

		//修改角色表
		if(this.updateById(role)){
			//
			this.sysRoleDeptService.saveOrUpdate(role.getId(),Arrays.asList(role.getDeptIds()));

			UserUtils.clearCachedAuthorizationInfo(); //清理权限缓存

			return true;
		}
		return false;
	}

	public List<SysRoleEntity> findByRolesByUserId(Long userId){
//		List<SysRoleEntity> userRole =  sysRoleMapper.getRolesByUserId(userId);
		List<SysRoleEntity> roles = this.list(new QueryWrapper<SysRoleEntity>().eq("status","0"));
//		for (SysRoleEntity r : roles) {
//			for (SysRoleEntity userR : userRole) {
//				if (r.getRoleId().longValue() == userR.getRoleId().longValue()){
//					r.setFlag(true);
//					break;
//				}
//			}
//		}
		return roles;
	}

}
