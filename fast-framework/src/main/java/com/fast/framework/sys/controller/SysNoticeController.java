package com.fast.framework.sys.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.utils.*;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.sys.entity.SysNoticeEntity;
import com.fast.framework.sys.service.SysNoticeService;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Global;
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
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/sys/notice")
public class SysNoticeController extends AbstractController {

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
    public R list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysNoticeService.findPage(params);
        return R.ok().put("page", page);
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
        sysNotice.setNoticeContent(FileUtil.readUtf8String("templates/modules/static/"+ sysNotice.getHtmlNo() + ".txt"));
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
    public R add(SysNoticeEntity sysNotice) {
        try{

            //校验参数
            ValidatorUtil.validateEntity(sysNotice);

            //生成静态文件
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 加载模板对象
            Template template = configuration.getTemplate("common/template/announcement.html");
            // 创建一个数据集
            Map<String, Object> data = new HashMap<>();
            data.put("noticeTitle",sysNotice.getNoticeTitle());
            data.put("noticeContent",sysNotice.getNoticeContent());
            data.put("createBy",getUser().getName());
            data.put("noticeLevel",sysNotice.getNoticeLevel());
            data.put("startTime",sysNotice.getStartTime());
            String uuid = IdUtil.fastSimpleUUID();

            // 指定文件输出路径以及文件名
            Writer out = new FileWriter(FileUtil.touch("templates/modules/static/"+ uuid + ".html"));
            //存放富文本编辑器内容
            File file = FileUtil.touch("templates/modules/static/"+ uuid + ".txt");
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
            return sysNoticeService.add(sysNotice) ? R.ok() : R.error();
        }catch (Exception e){
            e.printStackTrace();
            return  R.error();
        }
    }


    /**
     * 修改保存公告
     */
    @RequiresPermissions("sys:notice:edit")
    @BussinessLog(title = "公告通知", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public R editSave(SysNoticeEntity sysNotice) {

        try{

            //校验参数
            ValidatorUtil.validateEntity(sysNotice);
            //覆盖富文本内容
            FileUtil.writeUtf8String(sysNotice.getNoticeContent(),FileUtil.file("templates/modules/static/"+ sysNotice.getHtmlNo() + ".txt"));
            //删除
            FileUtil.del("templates/modules/static/"+ sysNotice.getHtmlNo() + ".html");

            //生成静态文件
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 加载模板对象
            Template template = configuration.getTemplate("common/template/announcement.html");
            // 创建一个数据集
            Map<String, Object> data = new HashMap<>();
            data.put("noticeTitle",sysNotice.getNoticeTitle());
            data.put("noticeContent",sysNotice.getNoticeContent());
            data.put("createBy",getUser().getName());
            data.put("noticeLevel",sysNotice.getNoticeLevel());
            data.put("startTime",sysNotice.getStartTime());

            // 指定文件输出路径以及文件名
            Writer out = new FileWriter(FileUtil.touch("templates/modules/static/"+ sysNotice.getHtmlNo() + ".html"));
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

            return sysNoticeService.update(sysNotice)? R.ok(): R.error();
        }catch (Exception e){
            e.printStackTrace();
            return  R.error();
        }
    }


    /**
     * 上传文件
     */
    @BussinessLog(title = "上传图片文件", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadFile")
    @RequiresPermissions("sys:notice:add")
    @ResponseBody
    public R uploadLic(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            StringBuffer url = request.getRequestURL();
            String contextPath = request.getServletContext().getContextPath();
            String sysUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
            String pathName = FileUploadUtil.uploadWeb(Global.getUploadPath(), file);
            return R.ok().put("fileName", pathName).put("url",sysUrl);
        }catch (Exception e){
            return R.error();
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
    public R delete(Long[] ids) {

        return sysNoticeService.deleteBatchByIds(ids)?R.ok():R.error();

    }
}
