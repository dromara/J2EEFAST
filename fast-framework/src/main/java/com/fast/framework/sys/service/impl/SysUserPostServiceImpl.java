package com.fast.framework.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.MapUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.dao.SysUserPostDao;
import com.fast.framework.sys.entity.SysUserPostEntity;
import com.fast.framework.sys.service.SysUserPostService;

import cn.hutool.core.util.ArrayUtil;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: SysUserPostServiceImpl
 * @Package: com.fast.framework.sys.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/3/2 11:19
 * @version V1.0
 
 *
 */
@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostDao, SysUserPostEntity>
        implements SysUserPostService {

    @Override
    public void saveOrUpdate(Long userId, List<String> postCodes) {
        // 先删除用户与角色关系
        this.removeByMap(new MapUtil().put("user_id", userId));

        if (ToolUtil.isEmpty(postCodes)) {
            return;
        }

        // 保存用户与角色关系
        List<SysUserPostEntity> list = new ArrayList<>(postCodes.size());
        for (String postCode : postCodes) {
            SysUserPostEntity post = new SysUserPostEntity();
            post.setUserId(userId);
            post.setPostCode(postCode);
            list.add(post);
        }

        this.saveBatch(list);
    }

    @Override
    public boolean deleteByUserIdBatch(Long[] userIds) {
        return this.remove(new QueryWrapper<SysUserPostEntity>().in("user_id",ArrayUtil.join(userIds,",")));
    }
}
