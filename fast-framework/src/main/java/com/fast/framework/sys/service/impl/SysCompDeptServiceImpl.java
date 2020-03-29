package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysCompDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.dao.SysCompDeptDao;
import com.fast.framework.sys.entity.SysCompDeptEntity;

/**
 * 
 * @Description:公司与地区对应关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 15:58
的
 *
 */
@Service("sysCompDeptService")
public class SysCompDeptServiceImpl extends ServiceImpl<SysCompDeptDao, SysCompDeptEntity>
		implements SysCompDeptService {

	/**
	 * 
	 * @Description:批量保存或者更新
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2018-12-05 16:10
	 * @param compId
	 * @param deptIdList
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long compId, List<Long> deptIdList) {
		// 先删除对应关系
		deleteBatch(new Long[] { compId });

		// 如果对于地区为0 不操作
		if (deptIdList == null || deptIdList.size() == 0) {
			return;
		}

		// 保存公司与地区对应关系
		List<SysCompDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysCompDeptEntity compDeptEntity = new SysCompDeptEntity();
			compDeptEntity.setCompId(compId);
			compDeptEntity.setDeptId(deptId);
			list.add(compDeptEntity);
		}

		// 批量插入
		this.saveBatch(list);
	}

	/**
	 * 
	 * @Description:根据公司ID获取对应地区
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2018-12-05 16:11
	 * @param compIds
	 * @return
	 *
	 */
	@Override
	public List<Long> queryDeptIdList(Long[] compIds) {
		return baseMapper.queryDeptIdList(compIds);
	}

	/**
	 * 
	 * @Description:根据公司ID批量删除对应地区
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2018-12-05 16:12
	 * @param compIds
	 * @return
	 *
	 */
	@Override
	public int deleteBatch(Long[] compIds) {
		return baseMapper.deleteBatch(compIds);
	}

}
