package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysAuthUserEntity;
import com.j2eefast.framework.sys.mapper.SysAuthUserMapper;
import com.j2eefast.framework.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysAuthUserService extends ServiceImpl<SysAuthUserMapper, SysAuthUserEntity> {


    public PageUtil findPage(Map<String, Object> params) {
        QueryWrapper<SysAuthUserEntity> queryWrapper = new QueryWrapper<SysAuthUserEntity>();
        queryWrapper.eq("user_id", UserUtils.getUserId());
        Page<SysAuthUserEntity> page = this.baseMapper.selectPage(new Query<SysAuthUserEntity>(params).getPage(), queryWrapper);
        return new PageUtil(page);
    }

    public boolean saveAuthUser(SysAuthUserEntity authUser){
        if(ToolUtil.isEmpty(this.getOne(new QueryWrapper<SysAuthUserEntity>().eq("uuid",authUser.getUuid())))){
            return this.save(authUser);
        }
        return false;
    }

    public SysAuthUserEntity selectAuthByUuidSource(String uuid, String source){
        return this.getOne(new QueryWrapper<SysAuthUserEntity>().eq("uuid",uuid).eq("source",source));
    }

}
