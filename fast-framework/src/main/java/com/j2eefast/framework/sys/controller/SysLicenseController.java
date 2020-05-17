package com.j2eefast.framework.sys.controller;

import cn.hutool.core.io.FileUtil;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.config.LicenseCheckListener;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.crypto.SM4;
import com.j2eefast.common.core.crypto.SoftEncryption;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.license.LicenseCheck;
import com.j2eefast.common.core.license.LicenseVerify;
import com.j2eefast.common.core.license.creat.LicenseCreator;
import com.j2eefast.common.core.license.creat.LicenseCreatorParam;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.Global;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>产品许可信息控制器</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-18 09:50
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Log4j
@Controller
@RequestMapping("/sys/license")
public class SysLicenseController extends BaseController {

    private String                  urlPrefix                   = "modules/sys/license";
    /**
     * 证书生成路径
     */
    @Value("${license.licensePath: license.lic}")
    private String licensePath;

    /////////////////////////////*****此处为生成许可证书配置参数正式项目应当分离////////////////////////
    @Value("${license.subject: }")
    private String subject;
    @Value("${license.privateAlias: }")
    private String privateAlias;
    @Value("${license.keyPass: }")
    private String keyPass;
    @Value("${license.storePass: }")
    private String storePass;
    @Value("${license.privateKeysStorePath: }")
    private String privateKeysStorePath;
    //////////////////////////////////////////////////////////

    @Autowired
    private LicenseCheckListener listener;

    /**
     * 产品名称
     */
    @Value("${productName: FastOs Demo}")
    private String productName;

    @RequiresPermissions("sys:license:view")
    @GetMapping()
    public String license(ModelMap mmap) {
        mmap.put("fastSN", HexUtil.encodeHexStr(SM4.encryptData_ECB(HexUtil.decodeHex
                (ConfigConstant.FAST_OS_SN),ConfigConstant.FAST_KEY)));
        mmap.put("productName",productName);
        mmap.put("authorizationTime",ConfigConstant.AUTHORIZATION_TIME);
        return urlPrefix + "/license";
    }


    /**
     * 上传许可文件
     */
    @BussinessLog(title = "上传许可文件", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadLic")
    @RequiresPermissions("sys:license:upload")
    @RepeatSubmit
    @ResponseBody
    public ResponseData uploadLic(@RequestParam("licfile") MultipartFile file){
        try{
            if (!file.isEmpty() && file.getOriginalFilename().equals("license.lic")){
                String pathName = FileUploadUtil.uploadFile(Global.getConifgFile(), file);

                //先拷贝之前的备份防止出故障 重命名
                FileUtil.rename(FileUtil.file(licensePath),"licenseReName",true,true);
                FileUtil.move(FileUtil.file(pathName),FileUtil.file(licensePath),true);
                //验证证书
                LicenseVerify licenseVerify = new LicenseVerify();
                //校验证书是否有效
                licenseVerify.install(listener.getVerifyParam());

                String path = FileUtil.getAbsolutePath(licensePath);
                FileUtil.del(path.substring(0,FileUtil.lastIndexOfSeparator(FileUtil.getAbsolutePath(path)))   +
                        File.separator+ "licenseReName.lic");
                return success();
            }
            return error(ToolUtil.message("sys.file.null"));
        }
        catch (Exception e)
        {
            String path = FileUtil.getAbsolutePath(licensePath);
            if(FileUtil.exist(path.substring(0,FileUtil.lastIndexOfSeparator(FileUtil.getAbsolutePath(path)))   +
                    File.separator+ "licenseReName.lic")){
                FileUtil.move(FileUtil.file(path.substring(0,FileUtil.lastIndexOfSeparator(FileUtil.getAbsolutePath(path)))   +
                        File.separator+ "licenseReName.lic"),FileUtil.file(licensePath),true);
            }

            try{
                //再装载老许可证书 -- 否则影响系统 也可以不处理 默认违规操作 必须重启服务器
                //验证证书
                LicenseVerify licenseVerify = new LicenseVerify();
                //校验证书是否有效
                licenseVerify.install(listener.getVerifyParam());
            }catch (Exception ex){
            }
            return error("许可证书无效,请确认无误在上传!");
        }
    }

    /**
     * 机器码校验
     */
    @RequestMapping(value = "/checkverifyNoUnique", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData checkverifyNoUnique(String sn, String verifyNo){

        try{
            //机器码解码
           byte[] clear = SM4.decryptData_ECB(HexUtil.decodeHex(sn),ConfigConstant.FAST_KEY);
           if(HexUtil.encodeHexStr(SM4.encryptData_ECB(clear,ConfigConstant.FAST_VERIFY_KEY))
                   .substring(0,6).toUpperCase().equals(verifyNo.toUpperCase())){
                return success();
           }else{
               return error("验证码不正确,请核对!");
           }
        }catch (Exception e){
            log.error("机器码校验失败",e);
            return error("机器码或验证码不正确,请核对!");
        }
    }

    @RequestMapping(value = "/creatLicense", method = RequestMethod.POST)
    @BussinessLog(title = "生成许可证书", businessType = BusinessType.INSERT)
    @RequiresPermissions("sys:license:creatLicense")
    @RequiresRoles(Constant.SU_ADMIN)
    @RepeatSubmit
    @ResponseBody
    public ResponseData creatLicense(String sn, String verifyNo, String ip){

        try{
            List<String> ipList = null;
            //校验IP
            if(ToolUtil.isNotEmpty(ip)){
                String[] ips = ip.split(",");
                ipList = new ArrayList<>(ips.length +1);
                for(String ipdrr: ips){
                    if(!ToolUtil.isBoolIp(ipdrr)){
                        return error("输入的IP地址不合法!");
                    }else{
                        ipList.add(ipdrr);
                    }
                }
            }
            ResponseData r = checkverifyNoUnique(sn, verifyNo);
            if(checkverifyNoUnique(sn, verifyNo).get("code").equals("00000")){
                //生成许可证书路径
                String folder = ToolUtil.createFolder(Global.getConifgFile());
                String name = ToolUtil.encodingFilename("license.lic");
                LicenseCreatorParam param = new LicenseCreatorParam();
                param.setSubject(subject);
                param.setPrivateAlias(privateAlias);
                param.setKeyPass(keyPass);
                param.setStorePass(storePass);
                param.setLicensePath(folder + name);
                param.setPrivateKeysStorePath(privateKeysStorePath);
                Calendar issueCalendar = Calendar.getInstance();
                //起始时间
                param.setIssuedTime(issueCalendar.getTime());
                Calendar expiryCalendar = Calendar.getInstance();
                //终止时间
                expiryCalendar.set(2050, Calendar.DECEMBER, 31, 23, 59, 59);
                param.setExpiryTime(expiryCalendar.getTime());
                param.setConsumerType("user");
                param.setConsumerAmount(1);
                param.setDescription("fast系统许可证书");
                LicenseCheck licenseCheck = new LicenseCheck();
                byte[] clear = SM4.decryptData_ECB(HexUtil.decodeHex(sn),ConfigConstant.FAST_KEY);
                licenseCheck.setFastSn(HexUtil.encodeHexStr(clear));
                if(ToolUtil.isNotEmpty(ipList)){
                    licenseCheck.setIpCheck(true);
                    licenseCheck.setIpAddress(ipList);
                }else{
                    licenseCheck.setIpCheck(false);
                }
                param.setLicenseCheck(licenseCheck);
                LicenseCreator licenseCreator = new LicenseCreator(param);
                // 生成license
                licenseCreator.generateLicense();
                //对下发文件进行加密
                //1.对路径加密
                String hexstr = HexUtil.encodeHexStr((folder + name).getBytes());
                //生成校验码
                byte[] send = HexUtil.BytesTo16K(hexstr.getBytes());
                String checkCode = SoftEncryption.encryptBySM4(send, ConfigConstant.FAST_MAC_KEY).getStr("hex").substring(0,6);
                return success().put("path",hexstr).put("checkCode",checkCode);
            }else{
                return error((String) r.get("msg"));
            }
        }catch (Exception e){
            return error("机器码或验证码不正确,请核对!");
        }
    }

    /**
     * 证书下载
     */
    @RequestMapping("/download")
    @RequiresPermissions("sys:license:creatLicense")
    public void download(HttpServletRequest request,
                         HttpServletResponse response, @RequestParam("path") String path, @RequestParam("checkCode") String checkCode) {
        try {
                byte[] send = HexUtil.BytesTo16K(path.getBytes());
                String b64 = SoftEncryption.encryptBySM4(send, ConfigConstant.FAST_MAC_KEY).getStr("hex").substring(0,6);
                if(b64.equals(checkCode)){
                    path = new String(HexUtil.decodeHex(path));
                    if(FileUtil.exist(path)){
                        String fileName = "license.lic";
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
                }
        }catch (Exception e){
            log.error("下载文件异常");
            return;
        }
    }
}
