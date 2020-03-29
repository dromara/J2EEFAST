package com.fast.generator.gen.controller;

import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.service.SysDictTypeSerive;
import com.fast.framework.sys.service.SysModuleService;
import com.fast.framework.utils.AbstractController;
import com.fast.generator.gen.entity.GenTableColumnEntity;
import com.fast.generator.gen.entity.GenTableEntity;
import com.fast.generator.gen.service.GenTableColumnService;
import com.fast.generator.gen.service.GenTableService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HashUtil;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @ClassName: GenController
 * @Package: com.fast.generator.controller
 * @Description: 码生成 操作处理
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/1/6 14:45
 * @version V1.0
 
 *
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends AbstractController {
    private String urlPrefix = "modules/tool/gen";

    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private SysDictTypeSerive sysDictTypeSerive;

    @Autowired
    private GenTableColumnService genTableColumnService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen(ModelMap mmap)
    {
        mmap.put("genTables", genTableService.list());
        return urlPrefix + "/gen";
    }

    /**
     * 查询代码生成列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params)
    {
        PageUtil page = genTableService.selectGenTableList(params);
        return R.ok().put("page", page);
    }


    /**
     * 查询数据表字段列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/column/list")
    @ResponseBody
    public R columnList(@RequestParam Map<String, Object> params)
    {
        List<GenTableColumnEntity> list =  genTableColumnService.selectGenTableColumnListByTableId(params);
        PageUtil page = new PageUtil(list,list.size(),50,1);
        return R.ok().put("page", page);
    }

    /**
     * 查询数据库列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/db/list")
    @ResponseBody
    public R dataList(@RequestParam Map<String, Object> params)
    {
        PageUtil page = genTableService.queryDbPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @BussinessLog(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    /**
     * 下载生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @BussinessLog(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    @RequiresPermissions("tool:gen:del")
    @BussinessLog(title = "代码生成", businessType = BusinessType.DELETE)
    @PostMapping("/del")
    @ResponseBody
    public R remove(Long[] ids)
    {
        genTableService.deleteGenTableByIds(ids);
        return R.ok();
    }


    @RequiresPermissions("tool:gen:code")
    @BussinessLog(title = "代码生成", businessType = BusinessType.DELETE)
    @PostMapping("/genCode")
    @ResponseBody
    public R genCode(Long tableId){
        try {
            if(genTableService.genCode(tableId)){
                return R.ok();
            }
        }catch (RxcException e){
            return R.error(e.getCode(),e.getMsg());
        }
        return R.error();
    }




    /**
     * 预览代码
     */
    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    public String preview(@PathVariable("tableId") Long tableId, ModelMap mmap) throws IOException
    {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        Map <String,String> map = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry : dataMap.entrySet()){
            String mapKey = entry.getKey(); //vm/java/entity.java.vm
            mapKey = mapKey.substring(mapKey.lastIndexOf("/"),mapKey.length());//entity.java.vm
            mapKey = mapKey.substring(1,mapKey.lastIndexOf("."));
            String mapValue = entry.getValue();
            if(mapKey.equals("sql")){
                map.put("run.sql",mapValue);
            }else{
                map.put(mapKey,mapValue);
            }

        }
        mmap.put("gen_code", map);
        return urlPrefix + "/codeView";
    }




//    @RequiresPermissions("tool:gen:edit")
//    @GetMapping("/selectDict")
//    @ResponseBody
//    public List<Ztree> preview() throws IOException
//    {
//        return  sysDictTypeSerive.dictTypeTreeData();
//    }

    /**
     * 修改代码生成业务
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap)
    {
        GenTableEntity table = genTableService.selectGenTableById(tableId);
        mmap.put("gen_table", table);
        return urlPrefix + "/edit";
    }

    /**
     * 修改保存代码生成业务
     */
    @RequiresPermissions("tool:gen:edit")
    @BussinessLog(title = "代码生成", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public R editSave(@Validated GenTableEntity genTable)
    {
        genTable.setUpdateBy(getLoginName());
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return R.ok();
    }

    /**
     * 导入表结构
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/importTable/{dbtype}")
    public String importTable(@PathVariable("dbtype") String dbtype,ModelMap mmap)
    {
        if(dbtype.equals("0")){
            mmap.put("dbTables", genTableService.selectDbTableList0());
            mmap.put("dbType", "0");
        }else{
            mmap.put("dbTables", genTableService.selectDbTableListC());
            mmap.put("dbType", "1");
        }
        return urlPrefix + "/importTable";
    }

    /**导入选择***/
    @GetMapping("/selectDb")
    public String selectDb()
    {
        return urlPrefix + "/selectDb";
    }


    /**
     * 导入表结构（保存）
     */
    @RequiresPermissions("tool:gen:list")
    @BussinessLog(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    @ResponseBody
    public R importTableSave(@RequestParam Map<String, Object> tables)
    {
        String[] tableNames = Convert.toStrArray(tables.get("tables"));
        String dbType = (String) tables.get("dbType");

        // 查询表信息
        List<GenTableEntity> tableList = genTableService.selectDbTableListByNames(tableNames,dbType);
        genTableService.importGenTable(tableList, getLoginName(),dbType);
        return R.ok();
    }



    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"LixinCode.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }


    @GetMapping("/getDirTree")
    public String selectDeptTree()
    {
        return urlPrefix + "/tree";
    }

    /**
     * 加载字典列表树
     */
    @GetMapping("/dirTreeData")
    @ResponseBody
    public List<Ztree> dirTreeData()
    {
        File[] roots = File.listRoots();//
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (int i=0; i< roots.length; i++) {
            Ztree ztree = new Ztree();
            String path = roots[i].getPath();
            ztree.setId((long) HashUtil.rsHash(path));
            ztree.setpId((long) -1);
            ztree.setName(path);
            ztree.setTitle(path);
            ztree.setIsParent(!FileUtil.isDirEmpty(roots[i]));
            ztrees.add(ztree);
        }
        return ztrees;
    }


    /**
     * 加载字典列表树
     */
    @RequestMapping(value = "/getTreeData", method = RequestMethod.POST)
    @ResponseBody
    public List<Ztree> getTreeData(Long pid, String path)
    {

        File[] file = FileUtil.ls(path);//
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (int i=0; i< file.length; i++) {
            if(file[i].isDirectory()){
                Ztree ztree = new Ztree();
                String path0 = file[i].getPath();
                ztree.setId((long) HashUtil.rsHash(file[i].getName()));
                ztree.setpId(pid);
                ztree.setName(file[i].getName());
                ztree.setTitle(path0);
                try {
                    ztree.setIsParent(!FileUtil.isDirEmpty(new File(path0)));
                }catch (Exception e){
                    ztree.setIsParent(false);
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }




    @GetMapping(value = "/inputEdit/{columnId}/{text}")
    public String inputEdit(@PathVariable("columnId") String columnId,@PathVariable("text") String text,ModelMap mmap)
    {
        mmap.put("text", text.equals("-")?"":text);
        mmap.put("columnId", columnId);
        return urlPrefix + "/inputEdit";
    }

    @GetMapping(value = "/selectMenu/{tableId}")
    public String selectMenu(@PathVariable("tableId") Long tableId,ModelMap mmap)
    {

        GenTableEntity table = genTableService.selectGenTableMenuById(tableId);
        List<SysModuleEntity>  modules = sysModuleService.list();
        mmap.put("modules", modules);
        mmap.put("gen_table", table);
        return urlPrefix + "/selectMenu";
    }

}
