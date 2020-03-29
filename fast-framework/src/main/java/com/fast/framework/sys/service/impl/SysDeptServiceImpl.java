package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.ToolUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysDeptService;
import com.fast.framework.utils.Constant;
import com.fast.framework.annotation.DataFilter;
import com.fast.framework.sys.dao.SysDeptDao;
import com.fast.framework.sys.entity.SysDeptEntity;

@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

	@Override
	@DataFilter(subDept = true, user = false, tableAlias="d")
	public List<SysDeptEntity> queryList(Map<String, Object> params) {

		Object type =  params.get("type");

		List<SysDeptEntity> deptList = this.list(new QueryWrapper<SysDeptEntity>()
				.like(ToolUtil.isNotEmpty((String)params.get("name")),"name",(String)params.get("name"))
				.eq(ToolUtil.isNotEmpty(type),"type",type)
				.apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER)));
//		for (SysDeptEntity sysDeptEntity : deptList) {
//			SysDeptEntity parentDeptEntity = this.selectById(sysDeptEntity.getParentId());
//			if (parentDeptEntity != null) {
//				sysDeptEntity.setParentName(parentDeptEntity.getName());
//			}
//		}
		return deptList;
	}

	@Override
	@DataFilter(subDept = true, tableAlias="d")
	public List<SysDeptEntity> selectDeptList(Map<String, Object> params) {
		return this.baseMapper.selectDeptList((String)params.get("name"),(String) params.get("type"),
				(String) params.get(Constant.SQL_FILTER));
	}

	@Override
	public boolean updateDept(SysDeptEntity dept) {
		return this.baseMapper.updateDept(dept) > 0;
	}

	@Override
	@DataFilter(subDept = true,  tableAlias="u")
	public List<SysDeptEntity> selectByDeptNameId(Map<String, Object> params) {
		return this.baseMapper.selectByDeptNameId((Long)params.get("compId"),(String) params.get(Constant.SQL_FILTER));
	}





	@Override
	public List<Ztree> selectDeptTree(Long type) {
		Map<String, Object> params = new HashMap<>();
		params.put("type",String.valueOf(type));
		List<SysDeptEntity> list = queryList(params);
		return initZtree(list);
	}

	@Override
	public List<Ztree> selectCompIdDeptTree(Long compId) {
		Map<String, Object> params = new HashMap<>();
		params.put("compId",compId);
		List<SysDeptEntity> list = selectByDeptNameId(params);
		return initZtree(list);
	}


	/**
	 * 对象转部门树
	 *
	 * @param deptList 部门列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysDeptEntity> deptList)
	{
		return initZtree(deptList, null);
	}

	/**
	 * 对象转部门树
	 *
	 * @param deptList 部门列表
	 * @param roleDeptList 角色已存在菜单列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysDeptEntity> deptList, List<String> roleDeptList)
	{

		List<Ztree> ztrees = new ArrayList<Ztree>();
//		boolean isCheck = ToolUtil.isNotEmpty(roleDeptList);
		for (SysDeptEntity dept : deptList)
		{
			if (Constant.DEPT_NORMAL.equals(dept.getStatus()))
			{
				Ztree ztree = new Ztree();
				ztree.setId(dept.getDeptId());
				ztree.setpId(dept.getParentId());
				ztree.setName(dept.getName());
				ztree.setTitle(dept.getName());
				ztree.setType(dept.getType()+"");
//				if (isCheck)
//				{
//					ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
//				}
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId) {
		// 部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		// 获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	@Override
	public boolean checkDeptNameUnique(SysDeptEntity dept) {

		Long deptId = ToolUtil.isEmpty(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDeptEntity info = this.getOne(new QueryWrapper<SysDeptEntity>().
				eq("name",dept.getName()).eq("parent_id",dept.getParentId()));
		if (ToolUtil.isNotEmpty(info) && info.getDeptId().longValue() != deptId.longValue())
		{
			return  false;
		}
		return true;
	}

	@Override
	public SysDeptEntity selectDeptById(Long deptId) {
		return this.baseMapper.selectDeptById(deptId);
	}



	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = queryDetpIdList(deptId);
			if (list.size() > 0) {
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
