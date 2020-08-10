package com.j2eefast.framework.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysPostEntity;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.framework.sys.mapper.SysPostMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
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
        String postCode = (String) params.get("postCode");
        String postName = (String) params.get("postName");
        String postType = (String) params.get("postType");
        String status = (String) params.get("status");
        Page<SysPostEntity> page = this.baseMapper.findPage(new Query<SysPostEntity>(params).getPage(),
                                                            postCode,
                                                            postName,
                                                            postType,
                                                            status,
                                                            (String) params.get(Constant.SQL_FILTER));
        return new PageUtil(page);
    }


    public List<SysPostEntity> getPostList(Map<String, Object> params){
        String postCode = (String) params.get("postCode");
        String postName = (String) params.get("postName");
        String postType = (String) params.get("postType");
        return this.baseMapper.getPostList(postCode,
                                           postName,
                                           postType,
                                           (String) params.get(Constant.SQL_FILTER));
    }

    /**
     * 获取所有岗位信息
     * @return
     */
    public List<SysPostEntity> getPostAll(){
        return SpringUtil.getAopProxy(this).getPostList(new HashMap<>(1));
    }

    public List<SysPostEntity> getPostByUserId(Long userId){
        return this.baseMapper.getPostByUserId(userId);
    }

    public String getPostByUserIdToStr(Long userId){
        List<SysPostEntity> postList = this.getPostByUserId(userId);
        if(ToolUtil.isNotEmpty(postList)){
            StringBuffer sb = new StringBuffer(StrUtil.EMPTY);
            for(SysPostEntity post: postList){
                sb.append(post.getPostCode()).append(StrUtil.COMMA);
            }
            return sb.substring(0,sb.length()-1);
        }else{
            return StrUtil.EMPTY;
        }
    }


    public boolean deleteBatchByIds(Long[] ids){
        return  this.removeByIds(Arrays.asList(ids));
    }
}
