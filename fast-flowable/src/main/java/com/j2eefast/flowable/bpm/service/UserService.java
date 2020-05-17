package com.j2eefast.flowable.bpm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.entity.UserEntity;
import com.j2eefast.flowable.bpm.mapper.UserMapper;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 22:22
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class UserService  extends ServiceImpl<UserMapper, UserEntity> {

	public boolean add(UserEntity user){
		return  this.save(user);
	}

	public boolean updateUser(UserEntity user){
		return this.updateById(user);
	}

	public boolean delById(String id){
		return this.removeById(id);
	}

	public List<UserRepresentation> findList(String filter){
		List<UserEntity> listUser = this.list(new QueryWrapper<UserEntity>().like(ToolUtil.isNotEmpty(filter),"DISPLAY_NAME_",filter));
		List<UserRepresentation> userRepresentations = new ArrayList<>(listUser.size());
		for(UserEntity user: listUser){
			RemoteUser remoteUser = new RemoteUser();
			remoteUser.setId(user.getId());
			remoteUser.setEmail(user.getEmail());
			remoteUser.setFirstName(user.getFirstName()+"("+user.getId()+")");
			userRepresentations.add(new UserRepresentation(remoteUser));
		}
		return userRepresentations;
	}
}
