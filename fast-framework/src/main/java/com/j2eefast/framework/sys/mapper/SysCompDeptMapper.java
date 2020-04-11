package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysCompDeptEntity;

import java.util.List;

/**
 * <p> 公司与地区 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysCompDeptMapper extends BaseMapper<SysCompDeptEntity> {
	/**
	 * 根据公司ID，获取地区ID列表
	 */
	List<Long> findDeptIdList(Long[] compIds);
}
