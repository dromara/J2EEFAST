package com.j2eefast.generator.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;

import java.util.List;

/**
 * <p> 代码生成 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumnEntity> {

	int updateGenTableColumn(GenTableColumnEntity genTableColumn);

	/**
	 * 根据表名称查询列信息
	 *
	 * @param tableName 表名称
	 * @return 列信息
	 */
	List<GenTableColumnEntity> selectDbTableColumnsByName(String tableName);


	int deleteGenTableColumnByIds(Long[] ids);
}
