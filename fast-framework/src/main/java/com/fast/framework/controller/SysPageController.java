package com.fast.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.framework.sys.entity.SysMenuEntity;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.service.SysMenuService;
import com.fast.framework.sys.service.SysModuleService;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fast.framework.utils.ConfigConstant;
import com.fast.framework.utils.Global;
import com.fast.framework.sys.service.SysConfigService;

/**
 * 系统核心页面视图跳转控制类
 */
@Controller
public class SysPageController{
//	@Autowired
//	private HttpServletRequest request; // 自动注入request
	@Autowired
	private SysModuleService sysModuleService;
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping(value = { "/", "index" })
	public String index(ModelMap mmap) {
		List<SysModuleEntity> modules = sysModuleService.selectSysModules(ShiroUtils.getUserEntity());
		Map<String, List<SysMenuEntity>> menuList = new HashMap<>();
		for(SysModuleEntity s: modules){
			List<SysMenuEntity> menu = sysMenuService.getUserModuleMenuList(ShiroUtils.getUserEntity().getUserId(),s.getModuleCode());
			menuList.put(s.getModuleCode(),menu);
		}
		mmap.put("modules",modules);
		mmap.put("menuList",menuList);
		mmap.put("user",ShiroUtils.getUserEntity());
		return "index";
	}
	
	// 切换主题
	@GetMapping("/sys/switchSkin")
	public String switchSkin(ModelMap mmap)
	{
		return "skin";
	}

	// 浏览器版本过低
	@GetMapping("upbw/index")
	public String upbw(ModelMap mmap)
	{
		return "modules/sys/upbw/index";
	}

	@GetMapping("statics/notice/{htmlNo}")
	public String statics(@PathVariable("htmlNo") String htmlNo)
	{
		return "modules/static/" + htmlNo;
	}

	@RequestMapping("login")
	public String login() {
		return "login";
	}

	@RequestMapping("main")
	public String main() {
		return "main";
	}

	@RequestMapping("404.html")
	public String notFound() {
		return "404";
	}

}
