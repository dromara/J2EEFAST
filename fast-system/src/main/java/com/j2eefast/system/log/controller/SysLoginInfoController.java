package com.j2eefast.system.log.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.log.service.SysLoginInfoSerice;
import com.j2eefast.common.core.controller.BaseController;

/**
 * 登陆日志控制器
 * @author zhouzhou
 * @date 2018-03-13 15:15
 */
@Controller
@RequestMapping("/sys/logininfo")
public class SysLoginInfoController extends BaseController {
	private String urlPrefix = "modules/log";
	
	@Autowired
	private SysLoginInfoSerice  sysLoginInfoService;

	@RequiresPermissions("sys:logininfo:view")
	@GetMapping()
	public String indfo() {
		return urlPrefix + "/logininfo";
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:logininfo:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysLoginInfoService.findPage(params);
		return success(page);
	}
	
	
	@RequestMapping("/del")
	@BussinessLog(title = "登陆日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("sys:logininfo:del")
	@ResponseBody
	public ResponseData del(Long[] ids) {
		return sysLoginInfoService.deleteBatchByIds(ids) ? success(): error("删除失败!");
	}
	
	@BussinessLog(title = "登陆日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("sys:logininfo:clean")
	@PostMapping("/clean")
	@ResponseBody
	@RepeatSubmit
	public ResponseData clean(){
		return sysLoginInfoService.cleanLog()?success():error("清空失败!");
	}
}
