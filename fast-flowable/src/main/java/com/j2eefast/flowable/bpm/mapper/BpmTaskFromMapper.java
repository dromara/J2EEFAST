package com.j2eefast.flowable.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.flowable.bpm.entity.BpmTaskFromEntity;
import org.apache.ibatis.annotations.Param;

public interface BpmTaskFromMapper  extends BaseMapper<BpmTaskFromEntity> {

	Page<BpmTaskFromEntity> findPage( IPage<?> params,
									  @Param("fromName") String fromName,
									  @Param("version") String version,
									  @Param("modelKey") String modelKey,
									  @Param("sql_filter") String sql_filter);
}
