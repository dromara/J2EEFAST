package com.j2eefast.framework.sys.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.FileUploadUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysFilesEntity;
import com.j2eefast.framework.sys.service.SysFileService;
import com.j2eefast.framework.utils.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统文件上传下载 控制类
 */
@Slf4j
@Controller
@RequestMapping("/sys/comm")
public class SysFileController extends BaseController {

    @Autowired
    private SysFileService sysFileService;

    @BussinessLog(title = "文件上传", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file){
        try{
            String path = Global.getConifgFile() + File.separator + "sysFile";
            String fileName = super.getPara("name");
            //文件名称
            String fileMd5 = ToolUtil.encodingFilename(fileName);
            //保存
            path = path + File.separator + DateFormatUtils.format(new Date(), "yyyy/MM/dd") + File.separator + fileMd5 +"."+ FileUploadUtil.getExtension(file);
            File file0 = FileUtil.touch(path);
            file.transferTo(file0);
            path = FileUtil.getAbsolutePath(file0);
            SysFilesEntity sysFile = new SysFilesEntity();
            sysFile.setFileMd5(fileMd5);
            sysFile.setFileName(fileName);
            sysFile.setFilePath(path);
            sysFile.setFileSize(new BigDecimal(FileUtil.size(file0)));
            if(sysFileService.save(sysFile)){
                return  success("上传成功").put("path",path).put("fileName",fileName)
                        .put("id",sysFile.getId()).put("fileMd5",sysFile.getFileMd5()).put("fileSize",sysFile.getFileSize());
            }else{
                FileUtil.del(file0);
                return error("上传失败!");
            }
        }catch (Exception e){
            return error(e.getMessage());
        }
    }


    @RequestMapping(value = "/fileList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData fileList(String bizId, String bizType){
        try{
            return success().put("fileList",sysFileService.selectSysFilesList(bizId,bizType));
        }catch (Exception e){
            return error(e.getMessage());
        }
    }


    @RequestMapping("/download")
    public void commDownload(HttpServletRequest request,
                             HttpServletResponse response, @RequestParam("fileId") Long fileId) {
        try {
            SysFilesEntity file =  sysFileService.getById(fileId);
            if(ToolUtil.isEmpty(file)){
                log.error("文件不存在");
                return;
            }
            String path = file.getFilePath();
            String  fileName = file.getFileName();
            if(FileUtil.exist(path)){
                //浏览器设置
                String userAgent = request.getHeader("User-Agent");
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                    //IE浏览器处理
                    fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                } else {
                    // 非IE浏览器的处理：
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                //下载的文件携带这个名称
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                //文件下载类型--二进制文件
                response.setContentType("application/octet-stream");
                File imageFile = FileUtil.file(path);
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());
                int len = 0;
                while (-1 != (len = fis.read(buffer, 0, buffer.length))) {
                    bos.write(buffer,0,len);
                }
                log.info("==============================下载包长度:!" + bos.size() +"   ========================");
                response.setHeader("Content-Length",bos.size()+ "");
                fis.close();
                ServletOutputStream sos = response.getOutputStream();
                sos.write(bos.toByteArray());
                sos.flush();
                sos.close();
                log.info("==============================下载完成![" + path +"]   ========================");
                return;
            }else{
                log.error("文件不存在");
                return;
            }
        }catch (Exception e){
            log.error("下载文件异常");
            return;
        }
    }

    @RequestMapping("/fileUploadView")
    public void fileUploadView(HttpServletRequest request,
                             HttpServletResponse response,
                               @RequestParam("fileId") Long fileId) {
        try {
            SysFilesEntity file =  sysFileService.getById(fileId);
            if(ToolUtil.isEmpty(file)){
                log.error("文件不存在");
                return;
            }
            String path = file.getFilePath();
            String  fileName = file.getFileName();
            if(FileUtil.exist(path)){
                //设置文件ContentType类型
                response.setContentType(HttpUtil.getMimeType(fileName));
                File imageFile = FileUtil.file(path);
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());
                int len = 0;
                while (-1 != (len = fis.read(buffer, 0, buffer.length))) {
                    bos.write(buffer,0,len);
                }
                log.info("==============================下载包长度:!" + bos.size() +"   ========================");
                response.setHeader("Content-Length",bos.size()+ "");
                fis.close();
                ServletOutputStream sos = response.getOutputStream();
                sos.write(bos.toByteArray());
                sos.flush();
                sos.close();
                log.info("==============================下载完成![" + path +"]   ========================");
                return;
            }else{
                log.error("文件不存在");
                return;
            }
        }catch (Exception e){
            log.error("下载文件异常");
            return;
        }
    }

    /**
     * 图片裁剪展示
     * @param request
     * @param response
     * @param filePath
     */
    @RequestMapping("/fileAvatarView")
    public void fileavAtarView(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam("filePath") String filePath) {
        try {
            String path = Global.getConifgFile()+filePath;
            String  fileName = FileUtil.getName(path);
            if(FileUtil.exist(path)){
                //设置文件ContentType类型
                response.setContentType(HttpUtil.getMimeType(fileName));
                File imageFile = FileUtil.file(path);
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());
                int len = 0;
                while (-1 != (len = fis.read(buffer, 0, buffer.length))) {
                    bos.write(buffer,0,len);
                }
                log.info("==============================下载包长度:!" + bos.size() +"   ========================");
                response.setHeader("Content-Length",bos.size()+ "");
                fis.close();
                ServletOutputStream sos = response.getOutputStream();
                sos.write(bos.toByteArray());
                sos.flush();
                sos.close();
                log.info("==============================下载完成![" + path +"]   ========================");
                return;
            }else{
                log.error("文件不存在");
                return;
            }
        }catch (Exception e){
            log.error("下载文件异常");
            return;
        }
    }


}
