package com.j2eefast.common.core.utils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.EnctryptTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import oshi.hardware.NetworkIF;

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
        try{
			return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		}catch (Exception e){
        	return code;
		}
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

	public static String encodingExcelFilename(String filename){
		filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
		return filename;
	}

	/**
	 * 通过Hutool工具类获取系统硬件信息
	 * @throws Exception
	 */
	public static void  getFastServerInfos() throws Exception {
        if(ToolUtil.isEmpty(ConfigConstant.FAST_OS_SN)){
			NetworkIF[] netwoeks = OshiUtil.getHardware().getNetworkIFs();
			String macAddress = "";
			List<String> IpList = new ArrayList<>();
			for(NetworkIF net: netwoeks){
				macAddress+=net.getMacaddr();
				String temp = StrUtil.join(",",net.getIPv4addr());
				if(ToolUtil.isNotEmpty(temp)){
					IpList.add(temp);
				}
			}
			//序列号
			String serialNumber = OshiUtil.getSystem().getSerialNumber();
			//处理器ID
			String processorID = OshiUtil.getProcessor().getProcessorID();
			//组装 系统机器码 mac串+序列号+处理器ID+程序系统路径+系统名称+主机名+系统架构+环境版本号  -->机器码  可以自行增加硬件信息确保唯一性
			String temp = macAddress + serialNumber + processorID
					+ SystemUtil.getUserInfo().getCurrentDir() + SystemUtil.getOsInfo().getName() + SystemUtil.getHostInfo().getName() +
					SystemUtil.getOsInfo().getArch() + SystemUtil.getJavaInfo().getVersion();
			//再将机器码加密成16位字符串
			ConfigConstant.FAST_OS_SN = EnctryptTools.SM4Mac(ConfigConstant.FAST_MAC_KEY,temp.getBytes());
			ConfigConstant.KEY = EnctryptTools.SM4Mac(HexUtil.decodeHex(ConfigConstant.KEY),HexUtil.decodeHex(ConfigConstant.FAST_OS_SN));
			ConfigConstant.FAST_IPS = IpList;
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

	/**
	 * 数组以某种分隔符拼装
	 * @param value Long数值
	 * @param s 分隔符
	 * @return 拼装之后的字符串
	 */
    public static String conversion(Long[] value, String s){
    	String src = "";
    	for(Long l: value){
			src += l+s;
		}
		return  src.substring(0,src.length()-s.length());
	}

	/**
	 * 判断ResponseData 是否成功
	 * @param responseData 返回页面数据
	 * @return true 成功
	 */
	public static boolean isSuccess(ResponseData responseData){
    	if(responseData.get("code").equals(ResponseData.DEFAULT_SUCCESS_CODE)){
			return true;
		}else{
    		return false;
		}
	}
}
