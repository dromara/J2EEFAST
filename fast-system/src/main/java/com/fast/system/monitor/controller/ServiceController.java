package com.fast.system.monitor.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fast.common.core.constants.ConfigConstant;
import com.fast.common.core.utils.R;
import com.fast.framework.utils.AbstractController;
import com.fast.system.monitor.domain.Server;

/**
 * 
 * @Description:服务监控控制类
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-04-03 12:40
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/sys/server")
public class ServiceController extends AbstractController {
	
	private final static Logger 					LOG 					= LoggerFactory.getLogger(ServiceController.class);
	private String 									urlPrefix 				= "modules/monitor";
	@Value("${fast.demoMode.enabled: false}")
	private Boolean 								demoMode;
	
	@RequiresPermissions("sys:server:view")
	@GetMapping()
	public String operlog(){
		return urlPrefix + "/server";
	}
 
	@RequestMapping(value = "/info" ,method = RequestMethod.POST)
	@ResponseBody
	public R info() {
		Server server = new Server();
		try {
			server.copyTo();
		} catch (Exception e) {
			LOG.error("报错:",e);
			return R.error("获取服务器信息异常");
		}
		if(demoMode) {
			//server.getSys().getComputerName();
			server.getSys().setComputerIp(ConfigConstant.DEOM_MODE_SHOW);
			server.getSys().setComputerName(ConfigConstant.DEOM_MODE_SHOW);
			server.getSys().setUserDir(ConfigConstant.DEOM_MODE_SHOW);
			server.getJvm().setHome(ConfigConstant.DEOM_MODE_SHOW);
			server.getJvm().setVersion(ConfigConstant.DEOM_MODE_SHOW);
		}
		return R.ok().put("server", server);
	}
}
