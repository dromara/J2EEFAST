package com.j2eefast.framework.sys.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.sys.entity.SysNoticeEntity;
import com.j2eefast.framework.sys.service.SysNoticeService;
import com.j2eefast.framework.utils.Global;
import com.j2eefast.framework.utils.UserUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>公告页面控制器</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-26 11:02
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/sys/notice")
public class SysNoticeController extends BaseController {

    private String                  urlPrefix                   = "modules/sys/notice";

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequiresPermissions("sys:notice:view")
    @GetMapping()
    public String module() {
        return urlPrefix + "/notice";
    }

    @RequestMapping("/list")
    @RequiresPermissions("sys:notice:view")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysNoticeService.findPage(params);
        return success(page);
    }

    /**
     * 新增公告配置
     */
    @GetMapping("/add")
    public String add() {
        return urlPrefix + "/add";
    }

    /**
     * 预览公告
     */
    @GetMapping("/preview/{htmlNo}")
    public String preview(@PathVariable("htmlNo") String htmlNo ) {
        return "modules/static/" + htmlNo;
    }


    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        SysNoticeEntity sysNotice = sysNoticeService.getById(noticeId);
        //获取富文本内容
        sysNotice.setNoticeContent(FileUtil.readUtf8String("templates/modules/static/"+ sysNotice.getHtmlNo() + "1.html"));
        mmap.put("notice", sysNotice);
        return urlPrefix + "/edit";
    }

    /**
     * 新增保存公告
     */
    @RepeatSubmit
    @RequiresPermissions("sys:notice:add")
    @BussinessLog(title = "公告通知", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData add(SysNoticeEntity sysNotice) {
        try{

            //校验参数
            ValidatorUtil.validateEntity(sysNotice);

            //生成静态文件
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 加载模板对象
            Template template = configuration.getTemplate("common/template/announcement.html");
            String uuid = IdUtil.fastSimpleUUID();
            // 创建一个数据集
            Map<String, Object> data = new HashMap<>();
            data.put("noticeTitle",sysNotice.getNoticeTitle());
//            data.put("noticeContent",sysNotice.getNoticeContent());
            data.put("createBy", UserUtils.getLoginName());
            data.put("htmlNo",uuid + "1");
            String path = getHttpServletRequest().getContextPath();
            data.put(ConfigConstant.CTX_STATIC,path);
            data.put("noticeLevel",sysNotice.getNoticeLevel());
            data.put("startTime",sysNotice.getStartTime());

            // 指定文件输出路径以及文件名
            Writer out = new FileWriter(FileUtil.touch("templates/modules/static/"+ uuid + "0.html"));
            //存放富文本编辑器内容
            File file = FileUtil.touch("templates/modules/static/"+ uuid + "1.html");
            FileUtil.writeUtf8String(sysNotice.getNoticeContent(),file);
            // 输出文件
            template.process(data, out);
            // 关闭流
            out.close();
            sysNotice.setHtmlNo(uuid);
            //如果开始时间大于当前时间 就是未发布 时间还未到
            if(DateUtil.compare(sysNotice.getStartTime(),new Date()) > 0){
                //状态
                sysNotice.setStatus("1");
            }else{
                sysNotice.setStatus("0");
            }



            return sysNoticeService.add(sysNotice) ? success() : success();
        }catch (Exception e){
            e.printStackTrace();
            return  success();
        }
    }


    /**
     * 修改保存公告
     */
    @RequiresPermissions("sys:notice:edit")
    @BussinessLog(title = "公告通知", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public ResponseData editSave(SysNoticeEntity sysNotice) {

        try{

            //校验参数
            ValidatorUtil.validateEntity(sysNotice);
            //覆盖富文本内容
            FileUtil.writeUtf8String(sysNotice.getNoticeContent(),FileUtil.file("templates/modules/static/"+ sysNotice.getHtmlNo() + "1.html"));
            //删除
            FileUtil.del("templates/modules/static/"+ sysNotice.getHtmlNo() + "0.html");

            //生成静态文件
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 加载模板对象
            Template template = configuration.getTemplate("common/template/announcement.html");
            // 创建一个数据集
            Map<String, Object> data = new HashMap<>();
            data.put("noticeTitle",sysNotice.getNoticeTitle());
            data.put("noticeContent",sysNotice.getNoticeContent());
            String path = getHttpServletRequest().getContextPath();
            data.put(ConfigConstant.CTX_STATIC,path);
            data.put("htmlNo",sysNotice.getHtmlNo() + "1");
            data.put("createBy",UserUtils.getLoginName());
            data.put("noticeLevel",sysNotice.getNoticeLevel());
            data.put("startTime",sysNotice.getStartTime());

            // 指定文件输出路径以及文件名
            Writer out = new FileWriter(FileUtil.touch("templates/modules/static/"+ sysNotice.getHtmlNo() + "0.html"));
            // 输出文件
            template.process(data, out);
            // 关闭流
            out.close();

            //如果开始时间大于当前时间 就是未发布 时间还未到
            if(DateUtil.compare(sysNotice.getStartTime(),new Date()) > 0){
                //状态
                sysNotice.setStatus("1");
            }else{
                sysNotice.setStatus("0");
            }

            return sysNoticeService.update(sysNotice)? success(): success();
        }catch (Exception e){
            e.printStackTrace();
            return  success();
        }
    }


    /**
     * 上传文件
     */
    @BussinessLog(title = "上传图片文件", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadFile")
    @RequiresPermissions("sys:notice:add")
    @ResponseBody
    public ResponseData uploadLic(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            StringBuffer url = request.getRequestURL();
            String contextPath = request.getServletContext().getContextPath();
            String sysUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
            String pathName = FileUploadUtil.uploadWeb(Global.getUploadPath(), file);
            return success().put("fileName", pathName).put("url",sysUrl);
        }catch (Exception e){
            return success();
        }
    }


    /**
     * 删除模块
     */
    @RepeatSubmit
    @BussinessLog(title = "公告通知", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("sys:notice:del")
    @ResponseBody
    public ResponseData delete(Long[] ids) {

        return sysNoticeService.deleteBatchByIds(ids)?success():success();

    }
}
