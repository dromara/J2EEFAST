package com.j2eefast.flowable.bpm.service;


import cn.hutool.core.util.IdUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.entity.BpmOaFormEntity;
import com.j2eefast.flowable.bpm.entity.StartProcessInstanceEntity;
import com.j2eefast.flowable.bpm.mapper.BpmOaFormMapper;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.utils.UserUtils;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * OA请假单Service接口
 * @author: ZhouZhou
 * @date 2020-04-20 22:19
 */
@Service
public class BpmOaFormService extends ServiceImpl<BpmOaFormMapper,BpmOaFormEntity> {

	@Resource
	private BpmOaFormMapper bpmOaFormMapper;
	@Resource
	@Lazy
	private BpmOaFormService bpmOaFormService;
	@Autowired
	private FlowableProcessInstanceService flowableProcessInstanceService;

	/**
	 * 页面分页查询
     */
	public PageUtil findPage(Map<String, Object> params) {
		QueryWrapper<BpmOaFormEntity> r = new QueryWrapper<BpmOaFormEntity>();
                              String userName = (String) params.get("userName");
                    r.like(ToolUtil.isNotEmpty(userName), "user_name", userName);
                        
                            String roleName = (String) params.get("roleName");
                    r.like(ToolUtil.isNotEmpty(roleName), "role_name", roleName);
                        
                                        String leaveDays = (String) params.get("leaveDays");
                    r.eq(ToolUtil.isNotEmpty(leaveDays), "leave_days", leaveDays);
        
                      String leaveType = (String) params.get("leaveType");
                    r.eq(ToolUtil.isNotEmpty(leaveType), "leave_type", leaveType);
        
                            String status = (String) params.get("status");
                    r.eq(ToolUtil.isNotEmpty(status), "status", status);
                    Page<BpmOaFormEntity> page = this.baseMapper.selectPage(new Query<BpmOaFormEntity>(params).getPage(), r);
		return new PageUtil(page);
    }

	/**
     * 批量删除
     */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBpmOaFormByIds(Long[] ids) {
		return this.removeByIds(Arrays.asList(ids));
	}

	public boolean add(BpmOaFormEntity bpmOaForm){
		return this.save(bpmOaForm);
	}



	/**
     * 单个删除
     */
	public boolean deleteBpmOaFormById(Long id) {
		return this.removeById(id);
	}

	/**
     * 保存
     */
	@Transactional(rollbackFor = Exception.class)
	public ResponseData saveBpmOaForm(BpmOaFormEntity bpmOaForm){

		bpmOaForm.setId(IdUtil.fastSimpleUUID());
		StartProcessInstanceEntity startProcessInstanceVo = new StartProcessInstanceEntity();
		startProcessInstanceVo.setBusinessKey(bpmOaForm.getId());
		startProcessInstanceVo.setCreator(String.valueOf(UserUtils.getUserId()));
		startProcessInstanceVo.setCurrentUserCode(String.valueOf(UserUtils.getUserId()));
		startProcessInstanceVo.setFormName(bpmOaForm.getFromName());
		startProcessInstanceVo.setSystemSn("system");
		startProcessInstanceVo.setProcessDefinitionKey(bpmOaForm.getModelKey());
		Map<String, Object> variables = new HashMap<>();
		variables.put("leaveDays", bpmOaForm.getLeaveDays());
		startProcessInstanceVo.setVariables(variables);
		ResponseData returnStart = flowableProcessInstanceService.startProcessInstanceByKey(startProcessInstanceVo);
		if(returnStart.get("code").equals("00000")){
			bpmOaForm.setProcessInstanceId(((ProcessInstance)returnStart.get("data")).getProcessInstanceId());
			return bpmOaFormService.add(bpmOaForm)? ResponseData.success(): ResponseData.error("数据插入失败!");
		}else{
			return returnStart;
		}
    }

	/**
     * 修改根居ID
     */
	public boolean updateBpmOaFormById(BpmOaFormEntity bpmOaForm) {
		return this.updateById(bpmOaForm);
	}

	/**
     * 根居ID获取对象
     */
	public BpmOaFormEntity getBpmOaFormById(String id){
		return this.getById(id);
	}

}
