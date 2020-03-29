package com.fast.framework.sys.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.service.SysDriveBreakdownService;

import com.fast.framework.sys.dao.SysDriveBreakdownDao;
import com.fast.framework.sys.entity.SysDriveBreakdownEntity;

@Service("sysDriveBreakdownService")
public class SysDriveBreakdownServiceImpl extends ServiceImpl<SysDriveBreakdownDao, SysDriveBreakdownEntity>
		implements SysDriveBreakdownService {

	public String getKey(String key) {
		if (ToolUtil.isNotEmpty(key)) {
			if (key.equals("N")) {
				key = "sys_drive_breakdown.mach_no";
			}
			if (key.equals("M")) {
				key = "tb_mach.maccode";
			}
			if (key.equals("V")) {
				key = "tb_mach.version";
			}
			if (key.equals("C")) {
				key = "tb_mach.license_no";
			}
			if (key.equals("X")) {
				key = "tb_line.line_name";
			}
			if (key.equals("G")) {
				key = "tb_comp.name";
			}
			return key;
		} else {
			return "";
		}
	}

	@Override
	public PageUtil queryAllPage(Map<String, Object> params) {
		String column = (String) params.get("key");
		String value = (String) params.get("value");
		Page<SysDriveBreakdownEntity> p = new Query<SysDriveBreakdownEntity>(params).getPage();// ;
		Page<SysDriveBreakdownEntity> page = p.setRecords(this.baseMapper.queryAllPage(p, getKey(column), value));
		return new PageUtil(page);
	}

	@Override
	public void delete(String machNo) {
		this.baseMapper.delete(new QueryWrapper<SysDriveBreakdownEntity>().eq("mach_no", machNo));
	}

}
