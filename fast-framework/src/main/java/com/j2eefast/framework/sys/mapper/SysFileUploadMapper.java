package com.j2eefast.framework.sys.mapper;

import com.j2eefast.framework.sys.entity.SysFileUploadEntity;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 *
 * sys_file_uploadDAO接口
 * @author: ZhouZhou
 * @date 2020-07-29 18:06
 */
public interface SysFileUploadMapper extends BaseMapper<SysFileUploadEntity> {

	/**
     * 自定义分页查询
     * @param  page
     * @param  SysFileUploadEntity 实体类
     */
     Page<SysFileUploadEntity> findPage(IPage<SysFileUploadEntity> page,
                                       @Param("sysFileUpload") SysFileUploadEntity sysFileUploadEntity,
                                       @Param("sql_filter") String sql_filter);

     SysFileUploadEntity selectSysFileUploadById(Serializable id);

     List<SysFileUploadEntity> selectList(SysFileUploadEntity sysFileUploadEntity);



     /**删除相关方法  使用mybatis-plus集成的 **/
}
