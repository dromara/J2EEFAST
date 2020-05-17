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
	SysCompEntity findCompById(@Param("compId") Long compId);


	List<Long> findDetpIdList(@Param("parentId") Long parentId);

}
