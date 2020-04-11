package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouzhou
 */
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

	/**
	 * 查询子部门ID列表
	 * @param parentId 上级部门ID
	 */
	List<Long> findDetpIdList(@Param("parentId") Long parentId);

	/**
	 * 通过id查询
	 * @param deptId
	 * @return
	 */
	SysDeptEntity findDeptById(Long deptId);

	/**
	 * 页面查询
	 * @param name
	 * @param type
	 * @param sql_file
	 * @return
	 */
	List<SysDeptEntity> findDeptList(@Param("name") String name,
									   @Param("type") String type,
									   @Param("sql_file") String sql_file);

	/**
	 * 根居公司id查询
	 * @param compId
	 * @param sql_file
	 * @return
	 */
	List<SysDeptEntity> findByDeptNameId(@Param("compId") Long compId,
										   @Param("sql_file") String sql_file);
}
