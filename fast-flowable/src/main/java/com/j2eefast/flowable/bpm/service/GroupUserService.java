package com.j2eefast.flowable.bpm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.flowable.bpm.entity.GroupUserEntity;
import com.j2eefast.flowable.bpm.mapper.GroupUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 22:30
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class GroupUserService extends ServiceImpl<GroupUserMapper, GroupUserEntity> {

	public boolean add(GroupUserEntity guser){
		return this.save(guser);
	}

	public boolean delByUserId(String userId){
		return this.removeByMap(new MapUtil().put("USER_ID_",userId));
	}

	public boolean delByGroupId(String groupId){
		return this.removeByMap(new MapUtil().put("GROUP_ID_",groupId));
	}
}
