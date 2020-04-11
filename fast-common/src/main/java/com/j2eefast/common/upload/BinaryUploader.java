package com.j2eefast.common.upload;

import cn.hutool.core.img.ImgUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.utils.ImageUtils;
import com.j2eefast.common.ueditor.PathFormat;
import com.j2eefast.common.ueditor.define.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader {

	public static final String FILEUEDITOR_BASE_URL = "/fileUeditor/";

	public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf) {
		FileItemStream fileStream = null; //原始上传
		MultipartFile fileStream2 = null; // Spring MVC 上传
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField()) {
					break;
				}
				fileStream = null;
			}

			if (fileStream == null) {
				// 原始上传无文件，则检查是否是Spring MVC上传 j2eefast
				MultipartFile file = null;
				if (request instanceof MultipartHttpServletRequest){
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
					Iterator<String> it = multiRequest.getFileNames();
					while (it.hasNext()) {
						file = multiRequest.getFile(it.next());
						break;
					}
				}
				if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
					fileStream2 = file;
				}
			}

			if (fileStream == null && fileStream2 == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream != null ? fileStream.getName() : fileStream2.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = fileStream != null ? fileStream.openStream() : fileStream2.getInputStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				int actionCode = ((Integer) conf.get("actionCode")).intValue();
				// 上传图片后，进行图片压缩
				if (actionCode == ActionMap.UPLOAD_IMAGE){

					// 如果开启了压缩图片
					if ((Boolean)conf.get("imageCompressEnable")){
						Integer maxWidth = (Integer)conf.get("imageCompressBorder");
						ImageUtils.thumbnails(new File(physicalPath), maxWidth, -1, null);
					}
				}
				String url = PathFormat.format(savePath);
				int index = url.indexOf(FILEUEDITOR_BASE_URL);
				if(index >= 0) {
					url = url.substring(index);
				}
				//解决返回路径问题 j2eefast
				storageState.putInfo("url", request.getContextPath()+ ConfigConstant.RESOURCE_URLPREFIX  + url );
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
