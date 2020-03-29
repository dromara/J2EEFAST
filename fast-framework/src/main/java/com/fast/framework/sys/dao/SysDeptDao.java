package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 部门管理
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId 上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);


	SysDeptEntity selectDeptById(Long deptId);

	List<SysDeptEntity> selectDeptList(@Param("name") String name,
									   @Param("type") String type,@Param("sql_file") String sql_file);

	int updateDept(SysDeptEntity dept);

	List<SysDeptEntity> selectByDeptNameId(@Param("compId") Long compId,@Param("sql_file") String sql_file);

}
