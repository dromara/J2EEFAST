package com.fast.framework.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysRoleModuleEntity;

import java.util.List;

/**
 * @version V1.0
 
 * @ClassName: SysRoleModuleDao
 * @Package: com.fast.framework.sys.dao
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/2/15 12:04
 */
public interface SysRoleModuleDao extends BaseMapper<SysRoleModuleEntity> {

    /**
     * 批量删除模块
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatch(Long[] ids);

    List<String> selectRoleModuleList(Long id);
}
