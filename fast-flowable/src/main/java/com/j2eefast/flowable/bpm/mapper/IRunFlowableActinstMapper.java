package com.j2eefast.flowable.bpm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IRunFlowableActinstMapper {
	/**
	 * 删除节点信息
	 * @param ids ids
	 */
	void deleteRunActinstsByIds(List<String> ids) ;
}
