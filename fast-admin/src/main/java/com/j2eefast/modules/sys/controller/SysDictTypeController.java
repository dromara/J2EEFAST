package com.j2eefast.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.sys.entity.SysDictTypeEntity;
import com.j2eefast.framework.sys.service.SysDictTypeSerive;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.UserUtils;
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
 * @ClassName: SysDictTypeController
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/18 14:47
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/dict")
public class SysDictTypeController extends BaseController {
    private String urlPrefixBase = "modules/sys/dict/type";

    @Autowired
    private SysDictTypeSerive sysDictTypeSerive;

    @RequiresPermissions("sys:dict:view")
    @GetMapping()
    public String dictType()
    {
        return urlPrefixBase + "/type";
    }

    @PostMapping("/list")
    @RequiresPermissions("sys:dict:list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysDictTypeSerive.findPage(params);
        return success(page);
    }


    /**
     * 查询字典详细
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap){
        mmap.put("dictHtml", sysDictTypeSerive.getById(dictId));
        mmap.put("dictList", sysDictTypeSerive.list());
        return "modules/sys/dict/data/data";
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add(){
        return urlPrefixBase + "/add";
    }

    /**
     * 校验字典类型
     */
    @RequestMapping(value = "/checkDictTypeUnique", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData checkDictTypeUnique(SysDictTypeEntity dictType){
        if(sysDictTypeSerive.checkDictTypeUnique(dictType)){
            return success();
        }
        return error("已经存在!");
    }

    /**
     * 新增保存字典类型
     */
    @BussinessLog(title = "字典类型", businessType = BusinessType.INSERT)
    @RequiresPermissions("sys:dict:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSave(@Validated SysDictTypeEntity dict)
    {
        if (!sysDictTypeSerive.checkDictTypeUnique(dict))
        {
            return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        if(sysDictTypeSerive.save(dict)){
            return success();
        }else{
            return error("新增失败!");
        }
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap)
    {
        mmap.put("dictHtml", sysDictTypeSerive.getById(dictId));
        return urlPrefixBase + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @BussinessLog(title = "字典类型", businessType = BusinessType.UPDATE)
    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editSave(@Validated SysDictTypeEntity dict)
    {
        if (!sysDictTypeSerive.checkDictTypeUnique(dict)) {
            return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return sysDictTypeSerive.updateById(dict)?success():error("修改失败!");
    }


    @BussinessLog(title = "字典类型", businessType = BusinessType.DELETE)
    @RequiresPermissions("sys:dict:del")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData del(Long[] ids){
        return sysDictTypeSerive.deleteBatchByIds(ids)?success():error("删除失败!");
    }


    /**
     * 选择字典树
     */
    @GetMapping("/getDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId,
                                 @PathVariable("dictType") String dictType,
                                 ModelMap mmap){
        mmap.put("columnId", columnId);
        mmap.put("dictHtml", sysDictTypeSerive.getOne(new
                QueryWrapper<SysDictTypeEntity>().eq("dict_type",dictType)));
        return urlPrefixBase + "/tree";
    }

    /**
     * 加载字典列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData(){
        List<Ztree> ztrees = sysDictTypeSerive.dictTypeTreeData();
        return ztrees;
    }

}
