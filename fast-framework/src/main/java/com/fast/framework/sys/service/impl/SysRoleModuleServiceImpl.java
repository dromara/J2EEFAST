package com.fast.framework.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.dao.SysRoleModuleDao;
import com.fast.framework.sys.entity.SysRoleModuleEntity;
import com.fast.framework.sys.service.SysRoleModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @ClassName: SysRoleModuleServiceImpl
 * @Package: com.fast.framework.sys.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/2/15 12:12
 * @version V1.0
 
 *
 */
@Service("sysRoleModuleService")
public class SysRoleModuleServiceImpl extends ServiceImpl<SysRoleModuleDao, SysRoleModuleEntity> implements SysRoleModuleService {
    @Override
    public int deleteBatch(Long[] ids) {
        return this.baseMapper.deleteBatch(ids);
    }

    @Override
    public List<String> selectRoleModuleList(Long id) {
        return this.baseMapper.selectRoleModuleList(id);
    }

    @Override
    public boolean deleRoleModule(Long id) {
        return this.remove(new QueryWrapper<SysRoleModuleEntity>().eq("role_id",id));
    }
}
