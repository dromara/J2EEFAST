package com.fast.framework.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;

import org.springframework.stereotype.Service;
import com.fast.framework.sys.dao.SysPostDao;
import com.fast.framework.sys.entity.SysPostEntity;
import com.fast.framework.sys.service.SysPostService;

/**
 *
 * @ClassName: 岗位信息
 * @Package: com.fast.framework.sys
 * @Description: 岗位信息ServiceImpl业务层处理
 * @author: ZhouHuan 自动生成
 * @time 2020-02-28
 
 * /----------------------------/
 * /---><---/
 * /----------------------------/
 */
@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        QueryWrapper<SysPostEntity> r = new QueryWrapper<SysPostEntity>();

            String postCode = (String) params.get("postCode");
        r.eq(ToolUtil.isNotEmpty(postCode), "post_code", postCode);

          String postName = (String) params.get("postName");
        r.like(ToolUtil.isNotEmpty(postName), "post_name", postName);

            String postType = (String) params.get("postType");
        r.eq(ToolUtil.isNotEmpty(postType), "post_type", postType);

          String status = (String) params.get("status");
        r.eq(ToolUtil.isNotEmpty(status), "status", status);

          
        Page<SysPostEntity> page = this.baseMapper.selectPage(new Query<SysPostEntity>(params).getPage(), r);
        return new PageUtil(page);
    }

    @Override
    public int updateSysPost(SysPostEntity sysPost) {
        return this.baseMapper.updateSysPost(sysPost);
    }

    @Override
    public int deleteSysPostById(Long postId){
        return this.baseMapper.deleteSysPostById(postId);
    }

    /**
    * 删除岗位信息对象
    *
    * @param ids 需要删除的数据ID
    * @return 结果
    */
    @Override
    public int deleteBatch(Long[] ids){
        return this.baseMapper.deleteBatch(ids);
    }


    @Override
    public boolean insertSysPost(SysPostEntity sysPost){
        return this.save(sysPost);
    }

    @Override
    public SysPostEntity selectSysPostById(Long postId)
    {
        return this.getOne(new QueryWrapper<SysPostEntity>().eq("post_id",postId));
    }

}
