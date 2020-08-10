package com.j2eefast.generator.gen.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.generator.gen.entity.ExampleTestEntity;
import com.j2eefast.generator.gen.service.ExampleTestService;

/**
 * 代码生成范例页面控制器
 * @author ZhouZhou
 * @date 2020-08-07 11:33
 */
@Controller
@RequestMapping("/gen/test")
public class ExampleTestController extends BaseController{

    private String prefix = "modules/gen/test";
    @Autowired
    private ExampleTestService exampleTestService;

    @RequiresPermissions("gen:test:view")
    @GetMapping()
    public String test(){
        return prefix + "/test";
    }
        
    @RequestMapping("/list")
    @RequiresPermissions("gen:test:list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params,ExampleTestEntity exampleTestEntity) {        
        PageUtil page = exampleTestService.findPage(params,exampleTestEntity);        
		return success(page);
    }    
            
    @GetMapping("/add")
    public String add(ModelMap mmap){
        return prefix + "/add";
    }

    /**
     * 新增
     */
    @RepeatSubmit
    @RequiresPermissions("gen:test:add")
    @BussinessLog(title = "代码生成范例", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addExampleTest(@Validated ExampleTestEntity exampleTest){
        //校验参数
        ValidatorUtil.validateEntity(exampleTest);
        return exampleTestService.addExampleTest(exampleTest)? success(): error("新增失败!");
    }    
    
    /**
     * 修改
     */
    @RequiresPermissions("gen:test:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap){
        ExampleTestEntity exampleTest = exampleTestService.findExampleTestById(id);
        mmap.put("exampleTest", exampleTest);
        return prefix + "/edit";
    }

    /**
     * 修改保存代码生成范例
     */
    @RepeatSubmit
    @RequiresPermissions("gen:test:edit")
    @BussinessLog(title = "代码生成范例", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editExampleTest(ExampleTestEntity exampleTest){
		ValidatorUtil.validateEntity(exampleTest);
        return exampleTestService.updateExampleTestById(exampleTest)? success(): error("修改失败!");
    }    

    /**
     * 删除
     */
    @RepeatSubmit
    @BussinessLog(title = "代码生成范例", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("gen:test:del")
    @ResponseBody
    public ResponseData del(Long[] ids) {
      return exampleTestService.delExampleTestByIds(ids)? success(): error("删除失败!");
    }    
}
