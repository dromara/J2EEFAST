package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysCompEntity;
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

	@DataFilter(subComp = true)
	public List<SysCompEntity> findList(Map<String, Object> params) {
		List<SysCompEntity> compList = this.list(new QueryWrapper<SysCompEntity>()
												.like(ToolUtil.isNotEmpty((String)params.get("name")),
														"name",(String)params.get("name"))
												.apply(params.get(Constant.SQL_FILTER) != null,
														(String) params.get(Constant.SQL_FILTER))
		);
		return compList;
	}

	public SysCompEntity findCompById(Long compId) {
		return sysCompMapper.findCompById(compId);
	}

	public List<Long> findDetpIdList(Long parentId) {
		return sysCompMapper.findDetpIdList(parentId);
	}


	/**
	 * 获取本身以及下级所有
	 * @param deptId
	 * @return
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
			//更新公司对应地区
			sysCompDeptService.saveOrUpdate(comp.getCompId(), comp.getDeptIdList());
			return true;
		}
		return false;
	}

	public boolean update(SysCompEntity comp){
		if(this.updateById(comp)){
			sysCompDeptService.saveOrUpdate(comp.getCompId(), comp.getDeptIdList());
			return true;
		}
		return false;
	}


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

	public List<Ztree> findCompTree(Long compId, Long userId) {
		List<SysCompEntity> list = null;
		if (userId.equals(Constant.SUPER_ADMIN)) {
			list = this.list();
		}else{
			list = this.list(new QueryWrapper<SysCompEntity>().eq("parent_id",compId).or().eq("comp_id",compId));
		}
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
				ztree.setId(comp.getCompId());
				ztree.setpId(comp.getParentId());
				ztree.setName(comp.getName());
				ztree.setTitle(comp.getName());
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}
}
