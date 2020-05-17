package com.j2eefast.generator.gen.controller;

import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.service.SysDatabaseService;
import com.j2eefast.framework.sys.service.SysDictTypeSerive;
import com.j2eefast.framework.sys.service.SysModuleService;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.UserUtils;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import com.j2eefast.generator.gen.service.GenTableColumnService;
import com.j2eefast.generator.gen.service.GenTableService;

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
 * @Package: com.j2eefast.generator.controller
 * @Description: 码生成 操作处理
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/1/6 14:45
 * @version V1.0
 
 *
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController {
    private String urlPrefix = "modules/tool/gen";

    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private SysDatabaseService sysDatabaseService;
    @Autowired
    private SysDictTypeSerive sysDictTypeSerive;

    @Autowired
    private GenTableColumnService genTableColumnService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen(ModelMap mmap){
        mmap.put("genTables", genTableService.list());
        return urlPrefix + "/gen";
    }

    /**
     * 查询代码生成列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params) {
        PageUtil page = genTableService.findPage(params);
        return success(page);
    }


    /**
     * 查询数据表字段列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/column/list")
    @ResponseBody
    public ResponseData columnList(@RequestParam Map<String, Object> params){
        List<GenTableColumnEntity> list =  genTableColumnService.selectGenTableColumnListByTableId(params);
        PageUtil page = new PageUtil(list,list.size(),50,1);
        return success(page);
    }

    /**
     * 查询数据库列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/db/list")
    @ResponseBody
    public ResponseData dataList(@RequestParam Map<String, Object> params){
        PageUtil page = genTableService.queryDbPage(params);
        return success(page);
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @BussinessLog(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException{
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
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException{
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    @RequiresPermissions("tool:gen:del")
    @BussinessLog(title = "代码生成", businessType = BusinessType.DELETE)
    @PostMapping("/del")
    @ResponseBody
    public ResponseData del(Long[] ids) {
        return genTableService.deleteGenTableByIds(ids)?
                success(): error("删除失败!");
    }


    @RequiresPermissions("tool:gen:code")
    @BussinessLog(title = "代码生成", businessType = BusinessType.DELETE)
    @PostMapping("/genCode")
    @ResponseBody
    public ResponseData genCode(Long tableId){
        try {
            if(genTableService.genCode(tableId)){
                return success();
            }
        }catch (RxcException e){
            return error(e.getCode(),e.getMsg());
        }
        return error("生成失败!");
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
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap){
        GenTableEntity table = genTableService.findGenTableById(tableId);
        List<SysDatabaseEntity> listDb =  sysDatabaseService.list();
        mmap.put("listDb",listDb);
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
    public ResponseData edit(@Validated GenTableEntity genTable) {
        genTableService.validateEdit(genTable);
        return genTableService.update(genTable)? success(): error("修改失败!");
    }

    /**
     * 导入表结构
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/importTable/{dbtype}")
    public String importTable(@PathVariable("dbtype") String dbtype,ModelMap mmap){
        mmap.put("dbType", dbtype);
        if(dbtype.equals(DataSourceContext.MASTER_DATASOURCE_NAME)){
            mmap.put("dbTables", genTableService.findDbTableList());
        }else{
            mmap.put("dbTables", genTableService.findNoDbTableList(dbtype));
        }
        return urlPrefix + "/importTable";
    }

    /**导入选择***/
    @GetMapping("/selectDb")
    public String selectDb(ModelMap mmap) {
       List<SysDatabaseEntity> listDb =  sysDatabaseService.list();
        mmap.put("listDb",listDb);
        return urlPrefix + "/selectDb";
    }


    /**
     * 导入表结构（保存）
     */
    @RequiresPermissions("tool:gen:list")
    @BussinessLog(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    @ResponseBody
    public ResponseData importTableSave(@RequestParam Map<String, Object> tables){
        String[] tableNames = Convert.toStrArray(tables.get("tables"));
        String dbType = (String) tables.get("dbType");
        // 查询表信息
        List<GenTableEntity> tableList = genTableService.selectDbTableListByNames(tableNames,dbType);
        genTableService.importGenTable(tableList, UserUtils.getLoginName(),dbType);
        return success();
    }



    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"fastos.zip\"");
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
    public List<Ztree> dirTreeData(){
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

        GenTableEntity table = genTableService.findGenTableMenuById(tableId);
        List<SysModuleEntity>  modules = sysModuleService.list();
        mmap.put("modules", modules);
        mmap.put("gen_table", table);
        return urlPrefix + "/selectMenu";
    }

}
