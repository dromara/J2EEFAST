package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysDeptEntity;
import com.j2eefast.framework.sys.mapper.SysDeptMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 部门管理
 * @author zhouzhou
 */
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper,SysDeptEntity> {

	@Resource
	private SysDeptMapper sysDeptMapper;

	@DataFilter(subDept = true, user = false, tableAlias="d")
	public List<SysDeptEntity> findPage(Map<String, Object> params) {
		String type = (String) params.get("type");
		return this.list(new QueryWrapper<SysDeptEntity>().eq("del_flag","0")
				.like(ToolUtil.isNotEmpty((String)params.get("name")),"name",(String)params.get("name"))
				.eq(ToolUtil.isNotEmpty(type),"type",type)
				.apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER)));
	}

	@DataFilter(subDept = true, tableAlias="d")
	public List<SysDeptEntity> findDeptList(Map<String, Object> params) {
		return sysDeptMapper.findDeptList((String)params.get("name"),(String) params.get("type"),
				(String) params.get(Constant.SQL_FILTER));
	}

	@DataFilter(subDept = true,  tableAlias="u")
	public List<SysDeptEntity> findByDeptNameId(Map<String, Object> params) {
		return sysDeptMapper.findByDeptNameId((Long)params.get("compId"),
				(String) params.get(Constant.SQL_FILTER));
	}

	public List<Ztree> findDeptTree(Long type) {
		Map<String, Object> params = new HashMap<>();
		params.put("type",String.valueOf(type));
		List<SysDeptEntity> list = findPage(params);
		return initZtree(list);
	}

	public List<Ztree> findCompIdDeptTree(Long compId) {
		Map<String, Object> params = new HashMap<>();
		params.put("compId",compId);
		List<SysDeptEntity> list = findByDeptNameId(params);
		return initZtree(list);
	}

	public List<Long> findDetpIdList(Long parentId) {
		return sysDeptMapper.findDetpIdList(parentId);
	}

	public SysDeptEntity findDeptById(Long deptId) {
		return sysDeptMapper.findDeptById(deptId);
	}

	public boolean checkDeptNameUnique(SysDeptEntity dept) {

		Long deptId = ToolUtil.isEmpty(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDeptEntity info = this.getOne(new QueryWrapper<SysDeptEntity>().
				eq("name", dept.getName()).eq("parent_id", dept.getParentId()));
		if (ToolUtil.isNotEmpty(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取下级集合
	 * @param deptId
	 * @return
	 */
	public List<Long> getSubDeptIdList(Long deptId) {
		// 部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		// 获取子部门ID
		List<Long> subIdList = findDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 对象转树
	 *
	 * @param deptList 列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysDeptEntity> deptList) {
		return initZtree(deptList, null);
	}

	/**
	 * 对象转树
	 *
	 * @param deptList 列表
	 * @param roleDeptList 角色已存在菜单列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysDeptEntity> deptList, List<String> roleDeptList) {

		List<Ztree> ztrees = new ArrayList<Ztree>();
		for (SysDeptEntity dept : deptList) {
			if (Constant.DEPT_NORMAL.equals(dept.getStatus())) {
				Ztree ztree = new Ztree();
				ztree.setId(dept.getDeptId());
				ztree.setpId(dept.getParentId());
				ztree.setName(dept.getName());
				ztree.setTitle(dept.getName());
				ztree.setType(dept.getType()+"");
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = findDetpIdList(deptId);
			if (list.size() > 0) {
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
