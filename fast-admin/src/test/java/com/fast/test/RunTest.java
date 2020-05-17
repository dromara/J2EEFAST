package com.fast.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.system.UserInfo;
import com.j2eefast.common.core.utils.YamlUtil;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>项目打包类</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-11 16:50
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class RunTest {
	@Test
	public void runFASTBin() {
		long startTime =   System.currentTimeMillis();
		try {
			String url  = new UserInfo().getCurrentDir() + File.separator+ "pom.xml";
			Document doc = XmlUtil.readXML(FileUtil.file(url));
			Element el = XmlUtil.getRootElement(doc);
			List<Element> ls = XmlUtil.getElements(el,"properties");
			for(Element e: ls ) {
				//读入文件
				String path =  new UserInfo().getCurrentDir() + File.separator+ "\\target\\classes\\run\\runFASTOSWindos.txt";

				String s = FileUtil.readString(path, "UTF-8");

				String jar = "fast-v"+ XmlUtil.elementText(e, "version") + "-" + DatePattern.PURE_DATE_FORMAT.format(new Date()) + ".jar";

				System.out.println( "fast-v"+ XmlUtil.elementText(e, "version") + "-" + DatePattern.PURE_DATE_FORMAT.format(new Date()) + ".jar");

				s = StrUtil.replace(s, "{$APP_NAME&}", jar);
				s = StrUtil.replace(s, "{$VERSION&}", XmlUtil.elementText(e, "version") + "-" + DatePattern.PURE_DATE_FORMAT.format(new Date()));
				FileUtil.writeString(s, FileUtil.touch(new UserInfo().getCurrentDir() + File.separator+ "\\target\\runFAST.bat" ), "UTF-8");
				//FileUtil.writeString(s, FileUtil.touch(new UserInfo().getCurrentDir() + File.separator+ "\\target\\classes\\runFAST.bat" ), "UTF-8");
				path =  new UserInfo().getCurrentDir() + File.separator+ "\\target\\classes\\run\\runFASTOSLinux.txt";
				s = FileUtil.readString(path, "UTF-8");
				s = StrUtil.replace(s, "{$APP_NAME&}", jar);
				s = StrUtil.replace(s, "{$VERSION&}", XmlUtil.elementText(e, "version") + "-" + DatePattern.PURE_DATE_FORMAT.format(new Date()));

				url  = new UserInfo().getCurrentDir() +File.separator+ "target\\classes\\application.yml";
				System.out.println(url);
				Map<?, ?> yamlMap  = YamlUtil.loadYaml(url);
				String active = (String) YamlUtil.getProperty(yamlMap, "spring.profiles.active");

				url  = new UserInfo().getCurrentDir() +File.separator+ "target\\classes\\application-"+active+".yml";
				yamlMap  = YamlUtil.loadYaml(url);
				int POST = (int) YamlUtil.getProperty(yamlMap, "server.port");
				s = StrUtil.replace(s, "{$APP_POST&}", String.valueOf(POST));
				byte[] b = s.getBytes("UTF-8");
				int size = 0;
				for(int i=0; i<b.length; i++) {
					if(b[i] == 0x0a) {
						size +=1;
					}
				}
				byte[] c = new byte[b.length -size];
				int k = 0;
				for(int i=0; i<b.length; i++) {
					if(b[i] != 0x0d) {
						c[k] = b[i];
						k++;
					}
				}
				FileUtil.writeBytes(c, FileUtil.touch(new UserInfo().getCurrentDir() + File.separator+ "\\target\\runFAST.sh" ));
				//FileUtil.writeBytes(c, FileUtil.touch(new UserInfo().getCurrentDir() + File.separator+ "\\target\\classes\\runFAST.sh" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long times = System.currentTimeMillis() - startTime;
		System.out.println("耗时:"+times);
	}
}
