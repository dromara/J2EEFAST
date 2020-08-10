package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysCompEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.mapper.SysCompMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 公司名称
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-05 08:58
 */
@Service
public class SysCompService extends ServiceImpl<SysCompMapper,SysCompEntity> {

	@Resource
	private SysCompDeptService sysCompDeptService;
	@Resource
	private SysCompMapper sysCompMapper;

//	@DataFilter(compAlias="c")
//	public List<SysCompEntity> findList(Map<String, Object> params) {
//		List<SysCompEntity> compList = new ArrayList<>();
//		if(ToolUtil.isEmpty((String)params.get("id"))){
//			compList = this.list(new QueryWrapper<SysCompEntity>()
//					.like(ToolUtil.isNotEmpty((String)params.get("name")),
//							"name",(String)params.get("name"))
//					.eq(ToolUtil.isNotEmpty((String)params.get("type")),"type",(String)params.get("type"))
//					.apply(params.get(Constant.SQL_FILTER) != null,
//							(String) params.get(Constant.SQL_FILTER))
//			);
//			return compList;
//		}else{
//			SysCompEntity  sysComp	= this.getOne(new QueryWrapper<SysCompEntity>()
//					.like(ToolUtil.isNotEmpty((String)params.get("name")),
//							"name",(String)params.get("name"))
//					.eq(ToolUtil.isNotEmpty((String)params.get("type")),"type",(String)params.get("type"))
//					.eq(ToolUtil.isNotEmpty((String)params.get("id")),"id",(String)params.get("id"))
//					.apply(params.get(Constant.SQL_FILTER) != null,
//							(String) params.get(Constant.SQL_FILTER))
//			);
//			compList.add(sysComp);
//			List<SysCompEntity>  levelComps = this.list(new QueryWrapper<SysCompEntity>().eq("parent_id",sysComp.getId()));
//			getLevelComps(compList, levelComps);
//			return compList;
//		}
//	}

//
	public List<SysCompEntity> findList(Map<String, Object> params) {
		params.put("type","0");
		return SpringUtil.getAopProxy(this).getDeptList(params);
	}

	@DataFilter(compAlias="c",deptAlias = "c")
	public List<SysCompEntity> getDeptList(Map<String, Object> params){
		String name = (String)params.get("name");
		String type = (String)params.get("type");
		String parentId = (String)params.get("parentId");
		String status = (String)params.get("status");
		String id = (String)params.get("id");
		return this.baseMapper.getDeptList(
											StrUtil.nullToDefault(id,""),
											StrUtil.nullToDefault(parentId,""),
											StrUtil.nullToDefault(name,""),
											StrUtil.nullToDefault(status,""),
											StrUtil.nullToDefault(type,""),
											(String) params.get(Constant.SQL_FILTER));
	}

	/**
	 * 根据角色ID查询机构（数据权限）
	 *
	 * @param roleId 角色ID
	 * @return 机构列表（数据权限）
	 */
	public List<Ztree> roleDeptTreeData(Long roleId) {
		List<Ztree> ztrees = new ArrayList<Ztree>();
		List<SysCompEntity> deptList = SpringUtil.getAopProxy(this).getDeptList(new HashMap<>());
		if (ToolUtil.isNotEmpty(roleId)) {
			List<String> roleDeptList = this.baseMapper.selectRoleDeptTree(roleId);
			ztrees = initZtree(deptList, roleDeptList);
		}
		else{
			ztrees = initZtree(deptList);
		}
		return ztrees;
	}

	/**
	 * 通过公司ID 查询公司下面所有信息包括子信息,屏蔽公司不能选择
	 * @param id
	 * @return
	 */
	public List<Ztree> getCompIdToLeveAll(Long id){
		List<SysCompEntity>  compList = getSubCompEntitys(id);
		List<Ztree> ztrees = new ArrayList<Ztree>();
		if(ToolUtil.isNotEmpty(compList)){
			for(SysCompEntity comp: compList){
				Ztree ztree = new Ztree();
				ztree.setId(comp.getId());
				ztree.setpId(comp.getParentId());
				ztree.setName(comp.getName());
				ztree.setTitle(comp.getName());
				ztree.setChkDisabled(comp.getType().equals("0"));
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	public SysCompEntity findCompById(Long id) {
		return sysCompMapper.findCompById(id);
	}

	public List<Long> findDetpIdList(Long parentId) {
		return sysCompMapper.findDetpIdList(parentId);
	}

	public List<SysCompEntity> getSubCompEntitys(Long id){
		// 公司及子公司ID列表
		List<SysCompEntity> compList = new ArrayList<>();
		SysCompEntity self = this.findCompById(id);
		if(ToolUtil.isNotEmpty(self)){
			compList.add(self);
			List<SysCompEntity>  levelComps = this.list(new QueryWrapper<SysCompEntity>().eq("parent_id",id));
			getLevelComps(compList, levelComps);
		}
		return compList;
	}

	public void getLevelComps(List<SysCompEntity> compList, List<SysCompEntity> levelComps){
		if(ToolUtil.isNotEmpty(levelComps)){
			for(SysCompEntity comp: levelComps){
				compList.add(comp);
				List<SysCompEntity>  zComps = this.list(new QueryWrapper<SysCompEntity>().eq("parent_id",comp.getId()));
				if(ToolUtil.isNotEmpty(zComps)){
					getLevelComps(compList,zComps);
				}
			}
		}
	}

	/**
	 * 获取本身以及下级所有
	 * @param deptId
	 * @return id集合
	 */
	public List<Long> getSubDeptIdList(Long deptId) {
		// 公司及子公司ID列表
		List<Long> compIdList = new ArrayList<>();
		//compIdList.add(deptId);

		// 获取子部门ID
		List<Long> subIdList = findDetpIdList(deptId);
		getDeptTreeList(subIdList, compIdList);

		return compIdList;
	}

	private void getDeptTreeList(List<Long> subIdList, List<Long> compIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = findDetpIdList(deptId);
			if (list!= null && list.size() > 0) {
				getDeptTreeList(list, compIdList);
			}
			compIdList.add(deptId);
		}
	}

	public boolean add(SysCompEntity comp){
		if(this.save(comp)){
			return true;
		}
		return false;
	}

	public boolean update(SysCompEntity comp){
		if(this.updateById(comp)){
			return true;
		}
		return false;
	}


	public boolean checkCompNameUnique(SysCompEntity comp) {
		Long compId = ToolUtil.isEmpty(comp.getId()) ? -1L : comp.getId();
		SysCompEntity info = this.getOne(new QueryWrapper<SysCompEntity>().
				eq("name",comp.getName()).eq("parent_id",comp.getParentId()));
		if (ToolUtil.isNotEmpty(info) && info.getId().longValue() != compId.longValue())
		{
			return  false;
		}
		return true;
	}

	/**
	 * 查询公司树
	 * @return
	 */
	public List<Ztree> findCompTree(String type) {
		Map<String,Object> mapp = new HashMap<>();
		mapp.put("type",type);
		List<SysCompEntity> list = SpringUtil.getAopProxy(this).getDeptList(mapp);
		List<Ztree> ztrees = initZtree(list);
		return ztrees;
	}

	public List<Ztree> initZtree(List<SysCompEntity> list) {
		return  initZtree(list,null);
	}

	public List<Ztree> initZtree(List<SysCompEntity> compList, List<String> roleCompList){

		List<Ztree> ztrees = new ArrayList<Ztree>();
		boolean isCheck = !ToolUtil.isEmpty(roleCompList);
		for (SysCompEntity comp : compList) {
			if (Constant.COMP_NORMAL.equals(comp.getStatus())) {
				Ztree ztree = new Ztree();
				ztree.setId(comp.getId());
				ztree.setpId(comp.getParentId());
				ztree.setName(comp.getName());
				ztree.setTitle(comp.getName());
				ztree.setType(comp.getType());
				if(isCheck){
					ztree.setChecked(roleCompList.contains(comp.getId() + comp.getName()));
				}
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}
}
