package com.fast.framework.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.framework.sys.entity.SysPoiEntity;

public interface SysPoiDao extends BaseMapper<SysPoiEntity> {
	
	List<SysPoiEntity> selectAll(IPage params, @Param("key") String key, @Param("value") String value,@Param("sql_filter") String sql_filter);
}
