package com.j2eefast.framework.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.utils.HttpContextUtil;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysFileUploadEntity;
import com.j2eefast.framework.sys.entity.SysFilesEntity;
import com.j2eefast.framework.sys.service.SysFileService;
import com.j2eefast.framework.sys.service.SysFileUploadService;
import org.apache.commons.lang3.time.DateFormatUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

public class FileUploadUtils {

    /**
     * 保存文件与业务关联数据
     * @param bizId  业务主键ID
     * @param bizType 业务类型
     */
    public static void saveFileUpload(Long bizId,String bizType){
        HttpServletRequest request = HttpContextUtil.getRequest();
        String fileUploads = request.getParameter(bizType);
        String fileDels = request.getParameter(bizType+"__del");
        SysFileUploadService sysFileUploadService = SpringUtil.getBean(SysFileUploadService.class);
        //删除关联
        if(ToolUtil.isNotEmpty(fileDels)){
            String[] dels = StrUtil.split(fileDels,StrUtil.COMMA);
            for(String fId: dels){
                sysFileUploadService.removeByBizId(fId,String.valueOf(bizId));
            }
        }
        //新增关联
        if(ToolUtil.isNotEmpty(fileUploads)){
            String[] files = StrUtil.split(fileUploads,StrUtil.COMMA);
            SysFileService sysFileService = SpringUtil.getBean(SysFileService.class);
            for(String fileId: files){
                if(sysFileUploadService.getSysFileUploadByBizId(fileId,String.valueOf(bizId))){
                    SysFilesEntity filesEntity = sysFileService.getSysFileById(Long.valueOf(fileId));
                    SysFileUploadEntity sysFileUploadEntity  = new SysFileUploadEntity();
                    sysFileUploadEntity.setBizId(bizId);
                    sysFileUploadEntity.setFileName(filesEntity.getFileName());
                    sysFileUploadEntity.setFileId(filesEntity.getId());
                    sysFileUploadEntity.setBizType(bizType);
                    sysFileUploadService.saveSysFileUpload(sysFileUploadEntity);
                }
            }
        }
    }

    /**
     * 业务逻辑层获取保存的裁剪图片路径
     * @param imgName 页面控件Name
     * @return
     */
    public static String getAvatarImg(String imgName){
        HttpServletRequest request = HttpContextUtil.getRequest();
        String imgBase64 = request.getParameter(imgName);
        return saveImgBase64(imgBase64);
    }

    /**
     * 图片裁剪保存图片信息
     * @param imgBase64
     * @return 返回裁剪控件图片存放相对路径
     */
    public static String saveImgBase64(String imgBase64) {

        if (imgBase64.equals(StrUtil.EMPTY)) {
            return null;
        }

        String extension = null;
        String type = StrUtil.subBetween(imgBase64, "data:", ";base64,");
        if (StrUtil.containsIgnoreCase(type, "image/jpeg")) {
            extension = "jpg";
        } else if (StrUtil.containsIgnoreCase(type, "image/gif")) {
            extension = "gif";
        } else {
            extension = "png";
        }

        String imageUrl = File.separator + "avatar"
                + File.separator +
                DateFormatUtils.format(new Date(), "yyyy/MM/dd") + File.separator + IdUtil.fastSimpleUUID() + "." + extension;


        String base64 = StrUtil.subAfter(imgBase64, "base64,", true);
        if (StrUtil.isBlank(base64)) {
            return null;
        }

        byte[] data = Base64.decode(base64);

        try {
            FileUtil.writeBytes(data, Global.getConifgFile() + imageUrl);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }
}
