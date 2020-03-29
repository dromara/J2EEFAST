package com.fast.framework.sys.dao;

import com.fast.framework.sys.entity.SysPostEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 *
 * @ClassName: 岗位信息
 * @Package: com.fast.framework.sys
 * @Description: sys_postDAO接口
 * @author: ZhouHuan
 * @time 2020-02-28
 
 * /----------------------------/
 * /---><---/
 * /----------------------------/
 */
public interface SysPostDao extends BaseMapper<SysPostEntity> {


    /**
     * 修改岗位信息
     * 
     * @param sysPost 岗位信息
     * @return 结果
     */
    int updateSysPost(SysPostEntity sysPost);


    /**
     * 删除岗位信息
     * 
     * @param postId 岗位信息ID
     * @return 结果
     */
    int deleteSysPostById(Long postId);

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatch(Long[] ids);

}
