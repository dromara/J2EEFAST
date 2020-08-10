package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.framework.sys.entity.SysPostEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 岗位 Mapper 接口
 * @author zhouzhou
 * @date 2019-03-08 21:20
 */
public interface SysPostMapper  extends BaseMapper<SysPostEntity> {

    /**
     * 页面查询分页
     * @param params
     * @param postCode
     * @param postType
     * @param status
     * @param sql_filter
     * @return
     */
    Page<SysPostEntity> findPage(IPage<?> params,
                                 @Param("postCode") String postCode,
                                 @Param("postName") String postName,
                                 @Param("postType") String postType,
                                 @Param("status") String status,
                                 @Param("sql_filter") String sql_filter);

    /**
     * 查询所有岗位数据集合
     * @param postCode
     * @param postName
     * @param postType
     * @return
     */
    List<SysPostEntity> getPostList(@Param("postCode") String postCode,
                                    @Param("postName") String postName,
                                    @Param("postType") String postType,
                                    @Param("sql_filter") String sql_filter);

    List<SysPostEntity> getPostByUserId(@Param("userId") Long userId);
}
