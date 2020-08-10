package com.j2eefast.framework.sys.service;


import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.framework.sys.entity.SysAreaEntity;
import com.j2eefast.framework.sys.entity.SysDeptEntity;
import com.j2eefast.framework.sys.mapper.SysAreaMapper;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
/**
 *
 * 行政区域Service接口
 * @author: ZhouZhou
 * @date 2020-06-04 23:52
 */
@Service
public class SysAreaService extends ServiceImpl<SysAreaMapper,SysAreaEntity> {

	@Resource
	private SysAreaMapper sysAreaMapper;

	/**
	 * mybaitis-plus 页面分页查询
     */
	public PageUtil findPage(Map<String, Object> params) {
        QueryWrapper<SysAreaEntity> queryWrapper = new QueryWrapper<SysAreaEntity>();
              String name = (String) params.get("name");
        queryWrapper.like(ToolUtil.isNotEmpty(name), "name", name);
          String level = (String) params.get("level");
         queryWrapper.eq(ToolUtil.isNotEmpty(level), "level", level);
                      String zipCode = (String) params.get("zipCode");
         queryWrapper.eq(ToolUtil.isNotEmpty(zipCode), "zip_code", zipCode);
          String areaCode = (String) params.get("areaCode");
         queryWrapper.eq(ToolUtil.isNotEmpty(areaCode), "area_code", areaCode);
           		Page<SysAreaEntity> page = sysAreaMapper.selectPage(new Query<SysAreaEntity>(params).getPage(), queryWrapper);
		return new PageUtil(page);
    }

    /**
     * 自定义分页查询，含关联实体对像
     */
	public PageUtil findPage(Map<String, Object> params,SysAreaEntity sysAreaEntity) {
		Page<SysAreaEntity> page = sysAreaMapper.findPage(new Query<SysAreaEntity>(params).getPage(), sysAreaEntity)  ;
		return new PageUtil(page);
	}

	public List<SysAreaEntity> selectList(SysAreaEntity sysAreaEntity){
		return this.baseMapper.selectAreaList(sysAreaEntity);
	}


	/**
	 * 多级联动数据查询
	 * @param params
	 * @return
	 */
	public PageUtil findSelectPage(Map<String, Object> params){
		String type = (String) params.get("type");
		if(ToolUtil.isNotEmpty(type) && type.equals("-1")){
			return null;
		}

		String pId = (String) params.get("pId");
		String name =(String) params.get("name");
		//初始化上传
		String searchValue = (String) params.get("searchValue");

		Page<SysAreaEntity> page = sysAreaMapper.selectPage(new Query<SysAreaEntity>(params).getPage(),
				new QueryWrapper<SysAreaEntity>().eq(ToolUtil.isNotEmpty(type),"level",type)
						.eq(ToolUtil.isNotEmpty(pId),"parent_id",pId)
						.eq(ToolUtil.isNotEmpty(searchValue),"id",searchValue)
						.like(ToolUtil.isNotEmpty(name),"name",name));
		//数据转换
		List<Ztree> list = new ArrayList<>();
		for(SysAreaEntity area: page.getRecords()){
			Ztree ztree = new Ztree();
			ztree.setId(area.getId());
			ztree.setpId(area.getParentId());
			ztree.setName(area.getName());
			list.add(ztree);
		}
		//数据输出前端分页
		return new PageUtil(list,page.getTotal(),page.getSize(),page.getCurrent());
	}

	/**
     * 批量删除
     */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteSysAreaByIds(Long[] ids) {
		return removeByIds(Arrays.asList(ids));
	}

	/**
     * 单个删除
     */
	public boolean deleteSysAreaById(Long id) {
		return removeById(id);
	}

	/**
     * 保存
     */
	public boolean saveSysArea(SysAreaEntity sysArea){
        return save(sysArea);
    }

	/**
     * 修改根居ID
     */
	public boolean updateSysAreaById(SysAreaEntity sysArea) {
		return updateById(sysArea);
	}

	/**
     * 根居ID获取对象
     */
	public SysAreaEntity getSysAreaById(Long id){
		return getById(id);
	}


	/**
	 * 通过地区ids 集合获取地区名称 逗号分割
	 * @param ids
	 * @return
	 */
	public String getAreaNames(String ids){
		if(ToolUtil.isEmpty(ids)) {
			return StrUtil.EMPTY;
		}
		List<SysAreaEntity> areaList = this.list(new QueryWrapper<SysAreaEntity>().in("id",ids.split(StrUtil.COMMA)));
		StringBuffer sb = new StringBuffer("");
		for(SysAreaEntity area: areaList){
			sb.append(area.getName()).append(StrUtil.COMMA);
		}
		if(sb.toString().length() > 0){
			return sb.substring(0 , sb.toString().length()-1);
		}else {
			return sb.toString();
		}
	}


	/**
	 * 获取地区所有数据树
	 * @return
	 */
	public List<Ztree> getAllAreaZtree(){
		List<SysAreaEntity> listArea = this.list();
		return initZtree(listArea);
	}

	/**
	 *  地区对象转树对象
	 * @param areaList
	 * @return
	 */
	public List<Ztree> initZtree(List<SysAreaEntity> areaList) {
		List<Ztree> ztrees = new ArrayList<Ztree>(areaList.size());
		for (SysAreaEntity area : areaList) {
				Ztree ztree = new Ztree();
				ztree.setId(area.getId());
				ztree.setpId(area.getParentId());
				ztree.setName(area.getName());
				ztree.setTitle(area.getName());
				ztrees.add(ztree);
		}
		return ztrees;
	}
}
