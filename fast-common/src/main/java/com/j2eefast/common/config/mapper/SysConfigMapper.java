package com.j2eefast.common.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.common.config.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>系统配置Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 16:04
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {

    /**
     * 根据key，查询value
     */
    SysConfigEntity queryByKey(@Param("paramKey") String paramKey);


    /**
     * 根据key，更新value
     */
    int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);
}
