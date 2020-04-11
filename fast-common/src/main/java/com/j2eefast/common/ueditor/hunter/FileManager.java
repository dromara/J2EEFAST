package com.j2eefast.common.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.ueditor.define.MultiState;
import org.apache.commons.io.FileUtils;

import com.j2eefast.common.ueditor.PathFormat;
import com.j2eefast.common.ueditor.define.AppInfo;
import com.j2eefast.common.ueditor.define.BaseState;
import com.j2eefast.common.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;

public class FileManager {
	public static final String FILEUEDITOR_BASE_URL = "/fileUeditor/";
	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	private String savePath = null;
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		this.savePath =  (String)conf.get( "savePath" );
	}
	
	public State listFile (HttpServletRequest request, int index ) {
		
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState(request, fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		
		return state;
		
	}

	public State delFile(String url){
		State state = null;
		if(!StrUtil.containsAny(url,this.savePath)){
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		// /fast/profile/fileUeditor/upload/image/20200330/1585563565753050797.png
		String filepath = this.rootPath+this.savePath +StrUtil.subAfter(FileUtil.normalize(url),FileUtil.normalize(this.savePath),false);
		if(!FileUtil.isFile(filepath)){
			return new BaseState( false, AppInfo.NOTFOUND_UPLOAD_DATA );
		}
		String extName = FileUtil.extName(filepath);
		if(!StrUtil.containsAny(extName,this.allowFiles)){
			return new BaseState( false, AppInfo.NOT_ALLOW_FILE_TYPE );
		}
		if(!FileUtil.del(filepath)){
			return new BaseState( false, AppInfo.NOTFOUND_UPLOAD_DATA );
		}
		return state = new BaseState( true ,AppInfo.SUCCESS);
	}

	
	private State getState (HttpServletRequest request, Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			// j2eefast 绝对路径转换虚拟路径
			String url = PathFormat.format( this.getPath( file ) );

			int index = url.indexOf(FILEUEDITOR_BASE_URL);
			if(index >= 0) {
				url = url.substring(index);
			}
			fileState.putInfo( "url", request.getContextPath()+ ConfigConstant.RESOURCE_URLPREFIX  + url );
			fileState.putInfo("name",  FileUtil.getName(file));
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
