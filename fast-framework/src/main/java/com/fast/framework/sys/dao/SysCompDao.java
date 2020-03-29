package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysCompEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysCompDao extends BaseMapper<SysCompEntity> {
	/**
	 * 查询子公司D列表
	 * 
	 * @param parentId 上级公司ID
	 */
	List<Long> queryDetpIdList(Long parentId);


	SysCompEntity selectCompById(Long deptId);


	int updateComp(SysCompEntity comp);


}
