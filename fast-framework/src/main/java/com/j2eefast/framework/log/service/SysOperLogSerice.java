package com.j2eefast.framework.log.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.log.entity.SysOperLogEntity;
import com.j2eefast.framework.log.mapper.SysOperLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-20 16:46
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class SysOperLogSerice extends ServiceImpl<SysOperLogMapper, SysOperLogEntity> {

	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String title = (String) params.get("title");
		String pabusinessTypes = (String)params.get("businessTypes");
		String status = (String) params.get("status");
		String operName = (String) params.get("operName");
		String beginTime = (String) params.get("beginTime");
		String endTime = (String) params.get("endTime");
		Page<SysOperLogEntity> page = this.baseMapper.selectPage(new Query<SysOperLogEntity>(params).getPage(),
				new QueryWrapper<SysOperLogEntity>()
						.like(ToolUtil.isNotEmpty(title), "title", title)
						.in(ToolUtil.isNotEmpty(pabusinessTypes),"business_type", ToolUtil.isNotEmpty(pabusinessTypes)? pabusinessTypes.split(","):null)
						.eq(ToolUtil.isNotEmpty(status), "status", status)
						.like(ToolUtil.isNotEmpty(operName), "oper_name", operName)
						.apply(ToolUtil.isNotEmpty(beginTime)," date_format(oper_time,'%y%m%d') >= date_format('"+beginTime+"','%y%m%d')")
						.apply(ToolUtil.isNotEmpty(endTime)," date_format(oper_time,'%y%m%d') <= date_format('"+endTime+"','%y%m%d')")
				);
		return new PageUtil(page);
	}

	/**
	 * 清空日志表
	 * @author zhouzhou
	 * @date 2020-03-08 20:37
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean cleanLog() {
		this.baseMapper.cleanLog();
		return true;
	}
}
