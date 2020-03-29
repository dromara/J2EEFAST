package com.fast.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysUserPostEntity;

import java.util.List;

/**
 * @version V1.0
 
 * @ClassName: SysUserPostService
 * @Package: com.fast.framework.sys.service
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/3/2 11:18
 */
public interface SysUserPostService extends IService<SysUserPostEntity> {
    void saveOrUpdate(Long userId, List<String> postCodes);

    /**
     * 根据用户ID数组，批量删除
     */
    boolean deleteByUserIdBatch(Long[] userIds);

}
