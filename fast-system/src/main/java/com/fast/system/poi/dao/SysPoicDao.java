package com.fast.system.poi.dao;

import com.fast.system.poi.entity.SysPoicEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 *
 * @ClassName: 报信息
 * @Package: com.fast.system.poi
 * @Description: sys_poiDAO接口
 * @author: ZhouHuan
 * @time 2020-01-08
 
 *
 */
public interface SysPoicDao extends BaseMapper<SysPoicEntity> {


    /**
     * 修改报信息
     * 
     * @param sysPoic 报信息
     * @return 结果
     */
    int update(SysPoicEntity sysPoic);


    /**
     * 删除报信息
     * 
     * @param id 报信息ID
     * @return 结果
     */
    int deleteSysPoicById(Long id);

    /**
     * 批量删除报信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatch(Long[] ids);

}
