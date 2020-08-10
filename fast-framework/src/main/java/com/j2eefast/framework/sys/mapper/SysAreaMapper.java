package com.j2eefast.framework.sys.mapper;

import com.j2eefast.framework.sys.entity.SysAreaEntity;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 *
 * sys_areaDAO接口
 * @author: ZhouZhou
 * @date 2020-06-04 23:52
 */
public interface SysAreaMapper extends BaseMapper<SysAreaEntity> {

	/**
     * 自定义分页查询
     * @param  page 
     * @param  sysAreaEntity 实体类
     */
     Page<SysAreaEntity> findPage(IPage<SysAreaEntity> page,@Param("sysArea") SysAreaEntity sysAreaEntity);


     List<SysAreaEntity> selectAreaList(SysAreaEntity sysAreaEntity);
     


     /**删除相关方法  使用mybatis-plus集成的 **/
}
