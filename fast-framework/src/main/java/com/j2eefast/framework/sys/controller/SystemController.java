package com.j2eefast.framework.sys.controller;

import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.sys.constant.factory.ConstantFactory;
import com.j2eefast.framework.sys.entity.SysMenuEntity;
import com.j2eefast.framework.sys.service.SysMenuService;
import com.j2eefast.framework.sys.service.SysModuleService;
import com.j2eefast.framework.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>通用控制器</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 16:33
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
public class SystemController extends BaseController {

	@Autowired
	private SysModuleService sysModuleService;
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 主页
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = { "/", "index","index.html" })
	public String index(ModelMap mmap) {
		LoginUserEntity user = UserUtils.getUserInfo();
		List<Map<String, Object>> modules = user.getModules();
		Map<String, List<SysMenuEntity>> menuList = new HashMap<>();
		for(Map<String, Object> s: modules){
			List<SysMenuEntity> menu = ConstantFactory.me().getMenuByUserIdModuleCode(user.getId(), (String) s.get("moduleCode"),user);
			menuList.put((String) s.get("moduleCode"),menu);
		}
		mmap.put("modules",modules);
		mmap.put("menuList",menuList);
		mmap.put("user",user);
		return "index";
	}


	// 切换主题
	@GetMapping("/sys/switchSkin")
	public String switchSkin(ModelMap mmap){
		return "skin";
	}

	//便签
	@GetMapping("/sys/note")
	public String note(ModelMap mmap){
		return "note";
	}

	// 浏览器版本过低
	@GetMapping("upbw/index")
	public String upbw(ModelMap mmap){
		return "modules/sys/upbw/index";
	}

	@GetMapping("static/notice/{htmlNo}")
	public String statics(@PathVariable("htmlNo") String htmlNo){
		return "modules/static/" + htmlNo;
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
