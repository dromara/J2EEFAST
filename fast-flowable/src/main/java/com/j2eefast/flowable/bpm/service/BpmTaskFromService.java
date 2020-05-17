package com.j2eefast.flowable.bpm.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.entity.BpmTaskFromEntity;
import com.j2eefast.flowable.bpm.mapper.BpmTaskFromMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-27 17:45
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class BpmTaskFromService extends ServiceImpl<BpmTaskFromMapper, BpmTaskFromEntity> {

	@Resource
	private BpmTaskFromMapper bpmTaskFromMapper;

	/**
	 * 页面翻页
	 * @param params
	 * @return
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String fromName = (String) params.get("fromName");
		String version = (String) params.get("version");
		String modelKey = (String) params.get("modelKey");
		Page<BpmTaskFromEntity> page = this.bpmTaskFromMapper.findPage(	new Query<BpmTaskFromEntity>(params).getPage(),
				StrUtil.nullToDefault(fromName,""),
				StrUtil.nullToDefault(version,""),
				StrUtil.nullToDefault(modelKey,""),
				(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

	public boolean add(BpmTaskFromEntity taskFrom){
		return this.save(taskFrom);
	}

	public boolean updateTaskFrom(BpmTaskFromEntity taskFrom){
		return this.updateById(taskFrom);
	}

	public BpmTaskFromEntity getTaskFromById(Long id){
		return this.getById(id);
	}

	public BpmTaskFromEntity getByDeploymentId(String deploymentId){
		return this.getOne(new QueryWrapper<BpmTaskFromEntity>().eq("process_definition_key",deploymentId));
	}
}
