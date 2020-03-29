package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysCompDeptService;
import com.fast.framework.sys.service.SysCompService;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.annotation.DataFilter;
import com.fast.framework.sys.dao.SysCompDao;
import com.fast.framework.sys.entity.SysCompEntity;
import com.fast.framework.sys.entity.SysUserEntity;

/**
 * 
 * @Description:公司
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 16:25
的
 *
 */
@Service("sysCompService")
public class SysCompServiceImpl extends ServiceImpl<SysCompDao, SysCompEntity> implements SysCompService {

	@Autowired
	private SysCompDeptService sysCompDeptService;


	/**
	 * 获取所有公司
	 */
	@Override
	@DataFilter(subComp = true)
	public List<SysCompEntity> queryList(Map<String, Object> params) {
		List<SysCompEntity> compList = this.list(new QueryWrapper<SysCompEntity>()
				.like(ToolUtil.isNotEmpty((String)params.get("name")),"name",(String)params.get("name"))
				.apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
				);
		return compList;
	}

	@Override
	public SysCompEntity selectCompById(Long compId) {
		return this.baseMapper.selectCompById(compId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {

		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId) {
		// 公司及子公司ID列表
		List<Long> compIdList = new ArrayList<>();
		//compIdList.add(deptId);

		// 获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, compIdList);

		return compIdList;
	}

	private void getDeptTreeList(List<Long> subIdList, List<Long> compIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = queryDetpIdList(deptId);
			if (list!= null && list.size() > 0) {
				getDeptTreeList(list, compIdList);
			}
			compIdList.add(deptId);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(SysCompEntity comp) {
		comp.setCreateTime(new Date());
		// 保存公司表
		this.save(comp);

		// 保存公司与地区关系
		sysCompDeptService.saveOrUpdate(comp.getCompId(), comp.getDeptIdList());
	}

	@Override
	public boolean updateComp(SysCompEntity comp) {
		//更新公司对应地区
		sysCompDeptService.saveOrUpdate(comp.getCompId(), comp.getDeptIdList());
		if(this.baseMapper.updateComp(comp) > 0){
			return true;
		}
		return false;
	}

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void update(SysCompEntity comp) {
//		comp.setCreateTime(new Date());
//		// 更新公司表
//		this.updateById(comp);
//
//		// 保存公司与地区关系
//		sysCompDeptService.saveOrUpdate(comp.getCompId(), comp.getDeptIdList());
//	}
	
	@Override
	public List<Long> getCompLong() {
		SysUserEntity currentUser = ShiroUtils.getUserEntity();
		//List<Long> listDept = sysUserDeptService.queryDeptIdList(currentUser.getUserId());
		
		List<Long> listDept = this.getSubDeptIdList(currentUser.getCompId());//SysCompService.queryDeptIdList(currentUser.getUserId());
		listDept.add(currentUser.getCompId());
		return listDept;
	}

	@Override
	public List<Ztree> selectCompTree(Long compId, Long userId) {
		List<SysCompEntity> list = null;
		if (userId.equals(Constant.SUPER_ADMIN)) {
			 list = this.list();
//			 SysCompEntity root = new SysCompEntity();
//			 root.setCompId(0L);
//			 root.setName("总公司");
//			 root.setParentId(-1L);
//			 root.setOpen(true);
//			 root.setStatus("0");
//			 list.add(root);
		}else{
			list = getSubCompList(compId);
			list.add(this.getById(compId));
		}
		List<Ztree> ztrees = initZtree(list);
		return ztrees;
	}



	@Override
	public List<SysCompEntity> selectCompPid(Long compId) {
		return this.list(new QueryWrapper<SysCompEntity>().eq("parent_id",compId));
	}

	@Override
	public List<SysCompEntity> selectList(Long compId,String name) {

		List<SysCompEntity> list = getSubCompList(compId);
		list.add(this.getById(compId));
		List<SysCompEntity> listF = null;
		if(ToolUtil.isNotEmpty(name)){
			listF = this.list(new QueryWrapper<SysCompEntity>().like("name",name));
		}
		if(ToolUtil.isNotEmpty(listF)){
			List<SysCompEntity> finalListF = listF;
			List<SysCompEntity> intersection = list.stream()
					.filter(item -> finalListF.stream().map(e -> e.getName()).collect(Collectors.toList())
							.contains(item.getName())).collect(Collectors.toList());
			return intersection;
		}
		return list;

	}

	@Override
	public boolean checkCompNameUnique(SysCompEntity comp) {
		Long compId = ToolUtil.isEmpty(comp.getCompId()) ? -1L : comp.getCompId();
		SysCompEntity info = this.getOne(new QueryWrapper<SysCompEntity>().
				eq("name",comp.getName()).eq("parent_id",comp.getParentId()));
		if (ToolUtil.isNotEmpty(info) && info.getCompId().longValue() != compId.longValue())
		{
			return  false;
		}
		return true;
	}


	public List<Ztree> initZtree(List<SysCompEntity> list) {
		return  initZtree(list,null);
	}


	/**
	 * 对象转部门树
	 *
	 * @param compList 公司列表
	 * @param roleCompList 角色已存在菜单列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysCompEntity> compList, List<String> roleCompList)
	{

		List<Ztree> ztrees = new ArrayList<Ztree>();
		boolean isCheck = !ToolUtil.isEmpty(roleCompList);
		for (SysCompEntity comp : compList)
		{
			if (Constant.COMP_NORMAL.equals(comp.getStatus()))
			{
				Ztree ztree = new Ztree();
				ztree.setId(comp.getCompId());
				ztree.setpId(comp.getParentId());
				ztree.setName(comp.getName());
				ztree.setTitle(comp.getName());
//				if (isCheck)
//				{
//					ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
//				}
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	public List<SysCompEntity> getSubCompList(Long compId) {
		// 公司及子公司ID列表
		List<SysCompEntity> compIdList = new ArrayList<>();
		//compIdList.add(deptId);

		// 获取子部门ID
		List<SysCompEntity> subIdList = selectCompPid(compId);
		getDeptTreeList0(subIdList, compIdList);

		return compIdList;
	}

	private void getDeptTreeList0(List<SysCompEntity> subIdList, List<SysCompEntity> compIdList) {
		for (SysCompEntity deptId : subIdList) {
			List<SysCompEntity> list = selectCompPid(deptId.getCompId());
			if (list!= null && list.size() > 0) {
				getDeptTreeList0(list, compIdList);
			}
			compIdList.add(deptId);
		}
	}

}
