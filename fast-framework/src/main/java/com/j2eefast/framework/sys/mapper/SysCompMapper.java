package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysCompEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 公司 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysCompMapper extends BaseMapper<SysCompEntity> {

	/**
	 * 查询公司
	 */
	SysCompEntity findCompById(@Param("id") Long id);


	List<Long> findDetpIdList(@Param("parentId") Long parentId);

	/**
	 * 根据角色ID查询部门
	 *
	 * @param roleId 角色ID
	 * @return 部门列表
	 */
	List<String> selectRoleDeptTree(Long roleId);

	/**
	 * 获取机构数据
	 * @param parentId
	 * @param name
	 * @param status
	 * @param sql_filter
	 * @return
	 */
	List<SysCompEntity> getDeptList(@Param("id") String id,
									@Param("parentId") String parentId,
									@Param("name") String name,
									@Param("status") String status,
									@Param("type") String type,
									@Param("sql_filter") String sql_filter);

}
