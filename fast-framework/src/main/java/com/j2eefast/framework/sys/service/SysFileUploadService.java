package com.j2eefast.framework.sys.service;


import com.j2eefast.framework.sys.entity.SysFileUploadEntity;
import com.j2eefast.framework.sys.mapper.SysFileUploadMapper;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.Arrays;
/**
 *
 * 系统上传文件业务关联Service接口
 * @author: ZhouZhou
 * @date 2020-07-29 18:06
 */
@Service
public class SysFileUploadService extends ServiceImpl<SysFileUploadMapper,SysFileUploadEntity> {
																								
	@Resource
	private SysFileUploadMapper sysFileUploadMapper;
		/**
	 * mybaitis-plus   单表页面分页查询
     */
	public PageUtil findPage(Map<String, Object> params) {
        QueryWrapper<SysFileUploadEntity> queryWrapper = new QueryWrapper<SysFileUploadEntity>();
           String fileId = (String) params.get("fileId");
         queryWrapper.eq(ToolUtil.isNotEmpty(fileId), "file_id", fileId);
          String fileName = (String) params.get("fileName");
        queryWrapper.like(ToolUtil.isNotEmpty(fileName), "file_name", fileName);
          String fileType = (String) params.get("fileType");
         queryWrapper.eq(ToolUtil.isNotEmpty(fileType), "file_type", fileType);
          String bizid = (String) params.get("bizid");
         queryWrapper.eq(ToolUtil.isNotEmpty(bizid), "bizid", bizid);
          String bizType = (String) params.get("bizType");
         queryWrapper.eq(ToolUtil.isNotEmpty(bizType), "biz_type", bizType);
                    		Page<SysFileUploadEntity> page = sysFileUploadMapper.selectPage(new Query<SysFileUploadEntity>(params).getPage(), queryWrapper);
				return new PageUtil(page);
    }

    /**
     * 自定义分页查询，含关联实体对像
     */
	public PageUtil findPage(Map<String, Object> params,SysFileUploadEntity sysFileUploadEntity) {
		Page<SysFileUploadEntity> page = sysFileUploadMapper.findPage(new Query<SysFileUploadEntity>(params).getPage(),
																	sysFileUploadEntity,
																	(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}
	/**
     * 批量删除
     */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteSysFileUploadByIds(Long[] ids) {
		return this.removeByIds(Arrays.asList(ids));
	}

	/**
     * 单个删除
     */
	public boolean deleteSysFileUploadById(Long id) {
		return this.removeById(id);
	}

	/**
     * 保存
     */
	public boolean saveSysFileUpload(SysFileUploadEntity sysFileUpload){
        return this.save(sysFileUpload);
    }

    public boolean removeByBizId(String fileId, String bizid){
		return  this.remove(new QueryWrapper<SysFileUploadEntity>().eq("file_id",fileId).eq("biz_id",bizid));
	}

	public boolean getSysFileUploadByBizId(String fileId, String bizid){
		if(this.count(new QueryWrapper<SysFileUploadEntity>().eq("file_id",fileId).eq("biz_id",bizid))> 0){
			return false;
		}
		return true;
	}

	/**
     * 修改根居ID
     */
	public boolean updateSysFileUploadById(SysFileUploadEntity sysFileUpload) {
		return this.updateById(sysFileUpload);
	}

	/**
     * 根居ID获取对象
     */
	public SysFileUploadEntity getSysFileUploadById(Long id){
				return sysFileUploadMapper.selectSysFileUploadById(id);
			}
}
