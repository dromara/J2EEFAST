package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysCompDeptEntity;

/**
 * 
 * @Description:公司与地区对应关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 15:51
的
 *
 */
public interface SysCompDeptDao extends BaseMapper<SysCompDeptEntity> {
	/**
	 * 根据公司ID，获取地区ID列表
	 */
	List<Long> queryDeptIdList(Long[] compIds);

	/**
	 * 根据公司ID数组，批量删除
	 */
	int deleteBatch(Long[] compIds);




}
