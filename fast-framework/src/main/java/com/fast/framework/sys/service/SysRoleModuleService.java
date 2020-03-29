package com.fast.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysRoleModuleEntity;

import java.util.List;

/**
 * @version V1.0
 
 * @ClassName: SysRoleModuleService
 * @Package: com.fast.framework.sys.service
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/2/15 12:11
 */
public interface SysRoleModuleService extends IService<SysRoleModuleEntity> {
    /**
     * @Description: 批量删除
     * @auther: ZhouHuan 自动生成
     * @date: 2020-02-14
     */
    int deleteBatch(Long[] ids);

    List<String> selectRoleModuleList(Long id);

    boolean deleRoleModule(Long id);

}
