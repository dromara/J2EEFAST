package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.framework.log.entity.SysLoginInfoEntity;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.mapper.SysModuleMapper;
import com.j2eefast.framework.utils.Constant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.j2eefast.framework.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模块服务实现类
 * @author zhouzhou
 * @date 2020-03-08 21:28
 */
@Service
public class SysModuleService  extends ServiceImpl<SysModuleMapper,SysModuleEntity> {

	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		QueryWrapper<SysModuleEntity> r = new QueryWrapper<SysModuleEntity>();
        String moduleName = (String) params.get("moduleName");
	    r.like(ToolUtil.isNotEmpty(moduleName), "module_name", moduleName);
	    String status = (String) params.get("status");
	    r.eq(ToolUtil.isNotEmpty(status), "status", status);
	    Page<SysModuleEntity> page = this.baseMapper.selectPage(new Query<SysModuleEntity>(params).getPage(), r);
	    return new PageUtil(page);
	}
	
	/**
	 * 批量删除
	 * @author zhouzhou
	 * @date 2020-03-08 21:30
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatchByIds(Long[] ids) {
		return this.removeByIds(Arrays.asList(ids));
	}
	
	
	public List<SysModuleEntity> selectSysModules(LoginUserEntity user) {
        List<SysModuleEntity> modules = null;
        if(!user.getId().equals(Constant.SUPER_ADMIN)){
            List<Long> rs =  user.getRoleList();
            modules = this.baseMapper.findModuleByRoleIds(rs);
        }else{
            modules = this.list(new QueryWrapper<SysModuleEntity>().eq("status","0"));
        }
        return modules;
    }
	
	 /**
     * 设置模块状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public int setRoles(Long id, String status) {
        return this.baseMapper.setStatus(id, status);
    }

}
