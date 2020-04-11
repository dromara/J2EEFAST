package com.j2eefast.common.core.utils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.license.service.AbstractServerInfos;
import com.j2eefast.common.core.license.service.LinuxServerInfos;
import com.j2eefast.common.core.license.service.WindowsServerInfos;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 高频使用工具类
 * @author zhouzhou
 * @date 2020-03-11 15:02
 */
public class ToolUtil{

	private static int 							counter 							= 0;
	
	/**
     * 获取随机字符,自定义长度
     *
     * @author zhouzhou
     * 2020-03-11 15:07
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
	
	/**
	 * 判断对象是否为空  true 不为空
	 * @author zhouzhou
	 * @date 2020-03-11 15:07
	 */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 
     * 对象是否为空 true 为空
     * @author zhouzhou
     * @date 2020-03-11 15:09
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if ("".equals(o.toString().trim())) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List<?>) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map<?, ?>) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set<?>) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在空对象
     *
     * @author zhouzhou
     * @Date 2020-03-11 15:09
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是空对象
     *
     * @author zhouzhou
     * @date 2020-03-11 15:09
     */
    public static boolean isAllEmpty(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 根据消息键和参数 获取消息 委托给Spring messageSource
     * 
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args){
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
    
    /**
	 * 
	 * 字节计算转换
	 * 
	 * <pre>
	 * StrUtil.convertFileSize(1024)   			= 1kB
	 * </pre>
	 * @author zhouzhou 18774995071@163.com
	 * @time 2019-04-03 12:29
	 * @param size 字节大小
	 * @return 转换后大小字符串
	 *
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb)
        {
            return StrUtil.format("{} GB", NumberUtil.round((float) size / gb,2));
        }
        else if (size >= mb)
        {
            float f = NumberUtil.round((float) size / mb,2).floatValue();
            return StrUtil.format(f > 100 ? "{} MB" : "{} MB", f);
        }
        else if (size >= kb)
        {
            float f = NumberUtil.round((float) size / kb,2).floatValue();
            return StrUtil.format(f > 100 ? "{}  KB" : "{}  KB", f);
        }
        else
        {
            return StrUtil.format("{} B", size);
        }
	}
	
	public static String getMessage(Exception e){
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		return sw.toString();
	}

	public static boolean isBoolIp(String ipAddress) {
		String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}


	public static String createFolder(String folder){
		folder += File.separator + DateUtil.format(new Date(),"yyyy") + File.separator + DateUtil.format(new Date(),"MM") +
				File.separator + DateUtil.format(new Date(),"dd")+ File.separator;
		FileUtil.mkdir(folder);
		return folder;
	}


	/**
	 * 编码文件名
	 */
	public static String encodingFilename(String fileName) {
		fileName = fileName.replace("_", " ");
		fileName = Md5Util.hash(fileName + System.nanoTime() + counter++);
		return fileName;
	}

	public static void  getFastServerInfos() throws Exception {
        if(ToolUtil.isEmpty(ConfigConstant.FAST_OS_SN)){
            //操作系统类型
            String osName = System.getProperty("os.name").toLowerCase();
            AbstractServerInfos abstractServerInfos = null;

            //根据不同操作系统类型选择不同的数据获取方法
            if (osName.startsWith("windows")) {
                abstractServerInfos = new WindowsServerInfos();
            } else if (osName.startsWith("linux")) {
                abstractServerInfos = new LinuxServerInfos();
            }else{//其他服务器类型
                abstractServerInfos = new LinuxServerInfos();
            }
            abstractServerInfos.getServerInfos();
        }
    }

    /**
     * 修正路径，将 \\ 或 / 等替换为 File.separator
     * @param path 待修正的路径
     * @return 修正后的路径
     */
    public static String path(String path){
        String p = StringUtils.replace(path, "\\", "/");
        p = StringUtils.join(StringUtils.split(p, "/"), "/");
        if (!StringUtils.startsWithAny(p, "/") && StringUtils.startsWithAny(path, "\\", "/")){
            p += "/";
        }
        if (!StringUtils.endsWithAny(p, "/") && StringUtils.endsWithAny(path, "\\", "/")){
            p = p + "/";
        }
        if (path != null && path.startsWith("/")){
            p = "/" + p; // linux下路径
        }
        return p;
    }

    /**
     * 获取工程源文件所在路径
     * @return
     */
    public static String getProjectPath(){
        String projectPath = "";
        try {
            File file = ResourceUtil.getResource("").getFile();
            if (file != null){
                while(true){
                    File f = new File(path(file.getPath() + "/src/main"));
                    if (f.exists()){
                        break;
                    }
                    f = new File(path(file.getPath() + "/target/classes"));
                    if (f.exists()){
                        break;
                    }
                    if (file.getParentFile() != null){
                        file = file.getParentFile();
                    }else{
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (FileNotFoundException e) {
            // 忽略异常
        } catch (IOException e) {
            // 忽略异常
        }
        // 取不到，取当前工作路径
        if (StringUtils.isBlank(projectPath)){
            projectPath = System.getProperty("user.dir");
        }
        return projectPath;
    }

    /**
     * 获取工程源文件所在路径
     * @return
     */
    public static String getWebappPath(){
        String webappPath = "";
        try {
            File file = ResourceUtil.getResource("").getFile();
            if (file != null){
                while(true){
                    File f = new File(path(file.getPath() + "/WEB-INF/classes"));
                    if (f.exists()){
                        break;
                    }
                    f = new File(path(file.getPath() + "/src/main/webapp"));
                    if (f.exists()){
                        return f.getPath();
                    }
                    if (file.getParentFile() != null){
                        file = file.getParentFile();
                    }else{
                        break;
                    }
                }
                webappPath = file.toString();
            }
        } catch (FileNotFoundException e) {
            // 忽略异常
        } catch (IOException e) {
            // 忽略异常
        }
        // 取不到，取当前工作路径
        if (StringUtils.isBlank(webappPath)){
            webappPath = System.getProperty("user.dir");
        }
        return webappPath;
    }
}
