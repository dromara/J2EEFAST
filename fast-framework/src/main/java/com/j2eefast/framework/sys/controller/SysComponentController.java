package com.j2eefast.framework.sys.controller;

import cn.hutool.core.io.FileUtil;
import com.j2eefast.common.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>组件控制类</p>
 *
 * @author: zhouzhou
 * @date: 2020-05-22 09:14
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/sys/component")
public class SysComponentController extends BaseController {

	private String                  urlPrefix                   = "modules/sys/macro";

	/**
	 * 跳转选择树页面
	 * @return
	 */
	@RequestMapping(value = "/treeselect", method = RequestMethod.POST)
	public String treeselect(ModelMap mmap) {
		// 树结构数据URL
		mmap.put("url",super.getPara("url"));
		//树节点ID
		mmap.put("treeId",super.getPara("treeId"));
		//节点名称
		mmap.put("treeName",super.getPara("treeName"));
		//关联ID
		mmap.put("correlationId",super.getPara("correlationId"));
		//是否展开树
		mmap.put("expandLevel",super.getPara("expandLevel"));
		//是否可以选中父节点
		mmap.put("isSelectParent",super.getPara("isSelectParent"));
		//是否可复选 单选还是多选
		mmap.put("checked", super.getPara("checked"));
		// 单选分组范围 同级互斥 - 或者  整个树互斥
		mmap.put("radioType",super.getPara("radioType"));
		//复选框级联选择规则 默认：{'Y':'ps','N':'ps'}
		mmap.put("chkboxType", super.getPara("chkboxType","{'Y':'ps','N':'ps'}"));
		//编辑回传此节点所有父节点
		mmap.put("parentIds",super.getPara("parentIds",""));
		return urlPrefix + "/treeselect";
	}

	/**
	 * 跳转图标选择
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "/iconselect", method = RequestMethod.POST)
	public String iconselect(ModelMap mmap) {
		mmap.put("iconValue", super.getPara("iconValue"));
		return urlPrefix + "/iconselect";
	}

	@GetMapping("/fileViwe")
	public String fileViwe(ModelMap mmap){
		String fileName = super.getPara("fileName");
		String fileUrl = super.getPara("fileUrl");
		String extName = FileUtil.extName(fileName);
		mmap.put("fileUrl",fileUrl);
		mmap.put("extName",extName);
		return urlPrefix + "/fileView";
	}

	/**
	 * 图片裁剪跳转
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "/cropperImg", method = RequestMethod.POST)
	public String cropperImg(ModelMap mmap) {
		mmap.put("imgValue", super.getPara("imgValue"));
		mmap.put("imageDefault", super.getPara("imageDefault"));
		mmap.put("ratio", super.getPara("ratio"));
		mmap.put("viewMode", super.getPara("viewMode"));
		return urlPrefix + "/cropperImg";
	}

}
