package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysFilesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFileMapper extends BaseMapper<SysFilesEntity> {

    /**
     * 通过业务ID 类型 查询文件列表
     * @param bizId 业务主键
     * @param bizType 业务类型
     * @return
     */
    List<SysFilesEntity> selectSysFilesList(@Param("bizId") String bizId,
                                            @Param("bizType") String bizType);
}
