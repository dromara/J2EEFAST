package com.fast.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysDictTypeEntity;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: fast
 * @Package: com.fast.framework.sys.service
 * @ClassName: SysDictTypeSerive
 * @Author: ZhouHuan Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/18 14:42
 * @Version: 1.0
 */
public interface SysDictTypeSerive extends IService<SysDictTypeEntity> {

    PageUtil queryPage(Map<String, Object> params);


    boolean checkDictTypeUnique(SysDictTypeEntity dictType);

    List<Ztree> dictTypeTreeData();

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(SysDictTypeEntity dictType);


    void deleteDictTypeByIds(Long[] ids);


}
