package com.j2eefast.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.redis.SysConfigRedis;
import com.j2eefast.framework.sys.entity.SysDictDataEntity;
import com.j2eefast.framework.sys.service.SysDictDataService;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: fast
 * @Package: com.j2eefast.modules.sys.controller
 * @ClassName: SysDictDataController
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/18 14:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/dict/data")
public class SysDictDataController extends BaseController {
    private String urlPrefix = "modules/sys/dict/data";

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysConfigRedis sysConfigRedis;

    @RequiresPermissions("sys:dict:view")
    @GetMapping()
    public String dictData() {
        return urlPrefix + "/data";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
	@RequiresPermissions("sys:dict:list")
    @ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysDictDataService.findPage(params);
		return success(page);
	}

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap)
    {
        mmap.put("dictType", dictType);
        return urlPrefix + "/add";
    }


    /**
     * 新增保存字典类型
     */
    @BussinessLog(title = "字典数据", businessType = BusinessType.INSERT)
    @RequiresPermissions("sys:dict:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSave(@Validated SysDictDataEntity dict)
    {
        if(sysDictDataService.save(dict)){
            List<SysDictDataEntity> list = sysDictDataService.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dict.getDictType()).
                    eq("status","0").orderBy(true, true, "dict_sort"));
            sysConfigRedis.saveOrUpdateDict(dict.getDictType(),list);
            return success();
        }else{
            return error("新增失败!");
        }
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap)
    {
        mmap.put("dictHtml", sysDictDataService.getById(dictCode));
        return urlPrefix + "/edit";
    }


    /**
     * 修改保存字典类型
     */
    @BussinessLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editSave(@Validated SysDictDataEntity dict) {
        if(sysDictDataService.updateDictData(dict)){
            return success();
        }else{
            return error("修改失败!");
        }
    }


    @BussinessLog(title = "字典数据", businessType = BusinessType.DELETE)
    @RequiresPermissions("sys:dict:del")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData del(Long[] ids) {
        return sysDictDataService.deleteBatchByIds(ids)?success():error("删除失败!");
    }
}
