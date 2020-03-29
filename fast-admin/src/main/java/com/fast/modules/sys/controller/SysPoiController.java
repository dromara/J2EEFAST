package com.fast.modules.sys.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.sys.entity.SysPoiEntity;
import com.fast.framework.sys.service.SysPoiService;

import cn.hutool.core.io.FileUtil;

@RestController
@RequestMapping("/sys/poic")
public class SysPoiController {
	private final static Logger logger = LoggerFactory.getLogger(SysPoiController.class);
	@Autowired
	private SysPoiService sysPoiService;
	
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page =  sysPoiService.queryAllPage(params);
		return R.ok().put("page", page);
	}
	
	
	@RequestMapping("/download")
	@RequiresPermissions("sys:poi:download")
	public void download(HttpServletRequest request, 
    		HttpServletResponse response,@RequestParam("id") String id) {
		SysPoiEntity  entity = sysPoiService.getById(id);
		if(entity == null) {
			return;
		}
		String path = entity.getPath();
        if(FileUtil.exist(path)) {
        	try {
        		String fileName = entity.getFilename();
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
                
        		File imageFile = new File(path);
	            FileInputStream fis = new FileInputStream(imageFile);
	            byte[] buffer = new byte[1024];
	            ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());  
	            int len = 0;
	            while (-1 != (len = fis.read(buffer, 0, buffer.length))) {
	            	bos.write(buffer,0,len);  
	            }
	            logger.info("==============================下载更新软件包长度:!" + bos.size() +"   ========================");
	            response.setHeader("Content-Length",bos.size()+ "");
	            fis.close();
	            ServletOutputStream sos = response.getOutputStream();
	            sos.write(bos.toByteArray());
	            sos.flush();
	            sos.close();
	            logger.info("==============================下载报表完成!" + entity.getFilename() +"   ========================");
	            return;
	        } catch (Exception e) {
	        	logger.error("下载失败!",e.getMessage());
	        	return;
	        }
        }else {
        	logger.error("文件不存在");
        	return;
        }
	}
}
