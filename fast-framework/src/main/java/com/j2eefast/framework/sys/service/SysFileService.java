package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.framework.sys.entity.SysFilesEntity;
import com.j2eefast.framework.sys.mapper.SysFileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFileService extends ServiceImpl<SysFileMapper, SysFilesEntity> {

    /**
     * 通过业务ID,类型查询 文件列表
     * @param bizId
     * @param bizType
     * @return
     */
    public List<SysFilesEntity> selectSysFilesList(String bizId, String bizType){
        return this.baseMapper.selectSysFilesList(bizId,bizType);
    }

    public SysFilesEntity getSysFileById(Long id){
        return this.getById(id);
    }
}
