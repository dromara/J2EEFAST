package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysUserDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDeptMapper extends BaseMapper<SysUserDeptEntity> {

	/**
	 * 根据用户ID，获取地区ID列表
	 */
	List<Long> findDeptIdList(@Param("userId") Long userId);

	List<Long> findUserIdList(@Param("deptId") Long deptId);

	List<SysUserDeptEntity> findListByUserId(@Param("userId") Long userId);

}
