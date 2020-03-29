package com.fast.framework.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysMachineErrorService;
import com.fast.framework.sys.dao.SysMachineErrorDao;
import com.fast.framework.sys.entity.SysMachineErrorEntity;

@Service("sysMachineErrorServiceImpl")
public class SysMachineErrorServiceImpl extends ServiceImpl<SysMachineErrorDao, SysMachineErrorEntity>
		implements SysMachineErrorService {

}
