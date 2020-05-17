package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysDictDataEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>字典值Mapper</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-05 15:26
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysDictDataMapper extends BaseMapper<SysDictDataEntity> {

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 *
	 * @param dictType 字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	String selectDictLabel(@Param("dictType") String dictType,
						   @Param("dictValue") String dictValue);

	/**
	 * 批量删除字典数据
	 *
	 * @param ids 需要删除的数据
	 * @return 结果
	 */
	int deleteDictDataByIds(Long[] ids);

}
