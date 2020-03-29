package com.fast.framework.log.service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.log.entity.SysLoginInfoEntity;
import com.fast.framework.log.mapper.SysLoginInfoMapper;
import com.fast.framework.utils.Constant;

@Service
public class SysLoginInfoSerice extends ServiceImpl<SysLoginInfoMapper, SysLoginInfoEntity>{
	
	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String username = (String) params.get("username");
		String ipaddr = (String) params.get("ipaddr");
		String status =  (String) params.get("status");
		String nostus = null;
		if(ToolUtil.isNotEmpty(status) && status.equals("-1")){
			nostus = "00000";
			status = null;
		}
		String beginTime = (String) params.get("beginTime");
		String endTime = (String) params.get("endTime");
		Page<SysLoginInfoEntity> page = this.baseMapper.selectPage(new Query<SysLoginInfoEntity>(params).getPage(),
				new QueryWrapper<SysLoginInfoEntity>()
						.like(ToolUtil.isNotEmpty(username), "username", username)
						.like(ToolUtil.isNotEmpty(ipaddr),"ipaddr",ipaddr)
						.eq(ToolUtil.isNotEmpty(status),"status",status)
						.ne(ToolUtil.isNotEmpty(nostus),"status",nostus)
						.apply(ToolUtil.isNotEmpty(beginTime)," date_format(login_time,'%y%m%d') >= date_format('"+beginTime+"','%y%m%d')")
						.apply(ToolUtil.isNotEmpty(endTime)," date_format(login_time,'%y%m%d') <= date_format('"+endTime+"','%y%m%d')")
						.apply(ToolUtil.isNotEmpty(params.get(Constant.SQL_FILTER)),
								(String) params.get(Constant.SQL_FILTER))
						);
		return new PageUtil(page);
	}
	
	/**
	 * 根居主键批量删除
	 * @author zhouzhou
	 * @date 2020-03-08 20:45
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatchByIds(Long[] logIds) {
		return this.removeByIds(Arrays.asList(logIds));
	}
	
	public SysLoginInfoEntity queryLoginByName(String username) {
		return this.getOne(new QueryWrapper<SysLoginInfoEntity>().eq("username", username)
				.eq("status", "00000").groupBy("login_time"));
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
