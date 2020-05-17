package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysUserPostEntity;
import com.j2eefast.framework.sys.mapper.SysUserPostMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>用户岗位关联</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-10 15:02
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class SysUserPostService  extends ServiceImpl<SysUserPostMapper, SysUserPostEntity> {

	public void saveOrUpdate(Long userId, List<String> postCodes) {
		// 先删除用户与角色关系
		this.removeByMap(new MapUtil().put("user_id", userId));

		if (ToolUtil.isEmpty(postCodes)) {
			return;
		}

		// 保存用户与角色关系
//		List<SysUserPostEntity> list = new ArrayList<>(postCodes.size());
		for (String postCode : postCodes) {
			SysUserPostEntity post = new SysUserPostEntity();
			post.setUserId(userId);
			post.setPostCode(postCode);
//			list.add(post);
			this.save(post);
		}
//		this.saveBatch(list);
	}

	public boolean deleteBatchByUserIds(Long[] userIds) {
		return this.remove(new QueryWrapper<SysUserPostEntity>().in("user_id",userIds));
	}
}
