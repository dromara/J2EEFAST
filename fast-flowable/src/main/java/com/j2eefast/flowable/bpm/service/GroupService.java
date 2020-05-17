package com.j2eefast.flowable.bpm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.entity.GroupEntity;
import com.j2eefast.flowable.bpm.mapper.GroupMapper;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.RemoteGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 15:48
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class GroupService extends ServiceImpl<GroupMapper, GroupEntity> {


	public boolean add(GroupEntity group){
		return this.save(group);
	}

	public boolean delById(String id){
		return this.removeById(id);
	}

	public List<GroupRepresentation> findList(String filter){
		List<GroupRepresentation> result = new ArrayList<>();
		List<GroupEntity> list =  this.list(new QueryWrapper<GroupEntity>().like(ToolUtil.isNotEmpty(filter),"NAME_",filter));
		for(GroupEntity g: list){
			RemoteGroup rGrop = new RemoteGroup();
			rGrop.setId(g.getId());
			rGrop.setName(g.getName()+"("+g.getType()+")");
			rGrop.setType(g.getType());
			result.add(new GroupRepresentation(rGrop));
		}
		return result;
	}
}
