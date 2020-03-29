package com.fast.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysPostEntity;
import com.fast.common.core.utils.PageUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: 岗位信息
 * @Package: com.fast.framework.sys
 * @Description: 岗位信息Service接口
 * @author: ZhouHuan
 * @time 2020-02-28
 
 * /----------------------------/
 * /---><---/
 * /----------------------------/
 */
public interface SysPostService extends IService<SysPostEntity> {

    /**
     * @Description: 查询表list分页
     * @param: 页面参数
     * @auther: ZhouHuan 自动生成
     * @date: 2020/1/7 10:31
     */
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @Description: 批量删除
     * @auther: ZhouHuan 自动生成
     * @date: 2020-02-28
     */
    int deleteBatch(Long[] ids);

    /**
     * 单个删除岗位信息
     *
     * @param postId 岗位信息ID
     * @return 结果
     */
    int deleteSysPostById(Long postId);

    /**
     * 修改岗位信息
     *
     * @param SysPostEntity 岗位信息
     * @return 结果
     */
    int updateSysPost(SysPostEntity sysPost);


    boolean insertSysPost(SysPostEntity sysPost);


    SysPostEntity selectSysPostById(Long postId);

}
