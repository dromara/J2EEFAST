package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysPostEntity;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.framework.sys.mapper.SysPostMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * 岗位信息Service接口
 * @author: zhouzhou
 * @time 2020-02-28
 */
@Service
public class SysPostService  extends ServiceImpl<SysPostMapper,SysPostEntity> {

    /**
     * 页面展示查询翻页
     */
    public PageUtil findPage(Map<String, Object> params) {
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


    public boolean deleteBatchByIds(Long[] ids){
        return  this.removeByIds(Arrays.asList(ids));
    }
}
