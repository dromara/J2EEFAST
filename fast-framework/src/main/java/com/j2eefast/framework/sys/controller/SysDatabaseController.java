package com.j2eefast.framework.sys.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.mutidatasource.annotaion.mybatis.OptionalSqlSessionTemplate;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.context.SqlSessionFactoryContext;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.sys.entity.SysMenuEntity;
import com.j2eefast.framework.sys.service.SysDatabaseService;
import com.j2eefast.framework.utils.Constant;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>多源数据控制器</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 20:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Log4j
@Controller
@RequestMapping("/sys/database")
public class SysDatabaseController  extends BaseController {

	private String                  urlPrefix                   = "modules/sys/database";

	@Autowired
	private SysDatabaseService sysDatabaseService;

	@RequiresPermissions("sys:database:view")
	@GetMapping()
	public String database() {
		return urlPrefix + "/database";
	}

	@RequestMapping("/list")
	@RequiresPermissions("sys:database:list")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysDatabaseService.findPage(params);
		return success(page);
	}




	/**
	 * 新增
	 */
	@GetMapping("/add")
	public String add() {
		return urlPrefix + "/add";
	}

	/**
	 * 新增保存模块
	 */
	@RepeatSubmit
	@RequiresPermissions("sys:database:add")
	@BussinessLog(title = "多源数据", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData addSave(@Validated SysDatabaseEntity database){
		//校验参数
		ValidatorUtil.validateEntity(database);
		if(sysDatabaseService.checkDataNameUnique(database)){
			return sysDatabaseService.add(database)?success():error("新增失败!");
		}else{
			return error("已经存在!");
		}
	}


	@RequestMapping(value = "/checkDataNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkDataNameUnique(SysDatabaseEntity database){
		return sysDatabaseService.checkDataNameUnique(database)?success():error("已经存在!");
	}

	/**
	 * 删除
	 */
	@RepeatSubmit
	@BussinessLog(title = "多源数据", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:database:del")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData delete(Long[] ids) {
		return sysDatabaseService.deleteBatchByIds(ids)?success():error("删除失败!");
	}

}
