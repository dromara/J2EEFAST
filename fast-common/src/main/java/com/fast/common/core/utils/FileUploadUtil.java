package com.fast.common.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.fast.common.core.exception.RxcException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fast.common.core.exception.InvalidExtensionException;
import com.fast.common.core.io.file.MimeType;

/**
 * @author zhouzhou
 * @date 2018-03-13 09:54
 */
public class FileUploadUtil {
	
	/**
     * 默认大小 50M
     */
    public static final long 					DEFAULT_MAX_SIZE 					= 50 * 1024 * 1024;
    /**
     * 默认的文件名最大长度 100
     */
    public static final int						DEFAULT_FILE_NAME_LENGTH 			= 100;
    /**
     * 默认上传的地址
     */
    private static String 						defaultBaseDir 						= "/";
    private static int 							counter 							= 0;

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
    	FileUploadUtil.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }



    /**
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String uploadFile(String baseDir,MultipartFile file) throws IOException
    {
        try
        {
            int fileNamelength = file.getOriginalFilename().length();
            if (fileNamelength > FileUploadUtil.DEFAULT_FILE_NAME_LENGTH){
                throw new RxcException("10004","文件太大");
            }

            assertAllowed(file, MimeType.DEFAULT_ALLOWED_EXTENSION);

            String fileName = extractFilename(file);

            File desc = getAbsoluteFile(baseDir, fileName);
            file.transferTo(desc);
            return baseDir + File.separator + fileName;
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String uploadWeb(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeType.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 返回上传成功的文件名
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws RxcException,
            InvalidExtensionException, IOException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtil.DEFAULT_FILE_NAME_LENGTH){
            throw new RxcException("10004","文件太大");
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateFormatUtils.format(new Date(), "yyyy/MM/dd") + File.separator + ToolUtil.encodingFilename(fileName) + "." + extension;
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }

    private static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        //D:/fast/uploadPath
        int dirLastIndex = uploadDir.lastIndexOf("/") + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName =  "/profile/" + currentDir + "/" + fileName;
        return ToolUtil.path(pathFileName);
    }



    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new RxcException("10004","文件太大");
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeType.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeType.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeType.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (ToolUtil.isEmpty(extension))
        {
            extension = MimeType.getExtension(file.getContentType());
        }
        return extension;
    }
}
