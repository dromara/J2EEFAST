package com.fast.framework.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysCompDeptEntity;

public interface SysCompDeptService extends IService<SysCompDeptEntity> {

	/**
	 * 保存或者更新公司与地区对应关系
	 */
	void saveOrUpdate(Long compId, List<Long> deptIdList);

	/**
	 * 根据公司ID，获取地区ID列表
	 */
	List<Long> queryDeptIdList(Long[] compIds);

	/**
	 * 根据公司ID数组，批量删除
	 */
	int deleteBatch(Long[] compIds);

}
