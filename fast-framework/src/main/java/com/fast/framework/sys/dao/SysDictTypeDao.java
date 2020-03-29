package com.fast.framework.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysDictTypeEntity;

/**
 * @ProjectName: fast
 * @Package: com.fast.framework.sys.dao
 * @ClassName: SysDictTypeDao
 * @Author: ZhouHuan Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/18 14:40
 * @Version: 1.0
 */
public interface SysDictTypeDao extends BaseMapper<SysDictTypeEntity> {

    SysDictTypeEntity checkDictTypeUnique(String dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictTypeEntity dictType);


    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteDictTypeByIds(Long[] ids);




}
