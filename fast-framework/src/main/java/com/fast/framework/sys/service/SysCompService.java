package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.base.entity.Ztree;
import com.fast.framework.sys.entity.SysCompEntity;

/**
 * 
 * @Description:公司名称
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 08:58
的
 *
 */
public interface SysCompService extends IService<SysCompEntity> {

	/**
	 * 获取公司名称
	 */
	List<SysCompEntity> queryList(Map<String, Object> params);


	SysCompEntity selectCompById(Long compId);

	/**
	 * 查询子公司ID列表
	 * 
	 * @param parentId 上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子公司ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);

	/**
	 * 保存
	 */
	void insert(SysCompEntity comp);

	/**
	 * 修改公司信息
	 */
	boolean updateComp(SysCompEntity comp);
	

	/**
	 * 通过用户查询权限信息
	 * @return
	 */
	List<Long> getCompLong();


	/**
	 * 查询所有公司
	 * @return
	 */
	List<Ztree> selectCompTree(Long compId, Long userId);


	List<SysCompEntity> selectCompPid(Long compId);



	List<SysCompEntity> selectList(Long compId, String name);


	boolean checkCompNameUnique(SysCompEntity comp);

}
