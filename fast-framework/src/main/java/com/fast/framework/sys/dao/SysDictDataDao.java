package com.fast.framework.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysDictDataEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 数据字典
 */
public interface SysDictDataDao extends BaseMapper<SysDictDataEntity> {

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(@Param("dictType") String dictType,
                           @Param("dictValue") String dictValue);

    int updateDictData(SysDictDataEntity dictData);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteDictDataByIds(Long[] ids);




}
