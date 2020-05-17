package com.j2eefast.framework.sys.controller;

import cn.hutool.core.io.FileUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.SoftEncryption;
import com.j2eefast.common.core.utils.HexUtil;
import com.j2eefast.framework.utils.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * <p>下载控制</p>
 *
 * @author: zhouzhou
 * @date: 2020-05-13 11:33
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
@Controller
public class SysDownloadContorller {


	@RequestMapping("/excel/download")
	@RequiresPermissions("*:export")
	public void download(HttpServletRequest request,
						 HttpServletResponse response, @RequestParam("fileName") String fileName) {
		try {
				String path = Global.getConifgFile() + File.separator + "pio" + File.separator + fileName;
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
					//删除
					FileUtil.del(path);
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
