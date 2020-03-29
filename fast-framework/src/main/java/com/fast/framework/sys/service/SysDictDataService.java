package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysDictDataEntity;

/**
 * 数据字典
 */
public interface SysDictDataService extends IService<SysDictDataEntity> {

	PageUtil queryPage(Map<String, Object> params);

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	List<SysDictDataEntity> selectDictDataByType(String dictType);

	String selectDictLabel(String dictType,
						   String dictValue);

	int countDictDataByType(String dictType);

	int updateDictData(SysDictDataEntity dictData);

	void deleteDictDataByIds(Long[] ids);

}
