package com.j2eefast.flowable.bpm.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.cmd.processinstance.DeleteFlowableProcessInstanceCmd;
import com.j2eefast.flowable.bpm.entity.BpmProcessDefinitionEntity;
import com.j2eefast.flowable.bpm.entity.ProcdefEntity;
import com.j2eefast.flowable.bpm.mapper.ProcessDefinitionMapper;
import com.j2eefast.framework.utils.Constant;
import org.apache.commons.io.IOUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-18 23:47
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
@Transactional(readOnly = true)
public class ProcdefService  extends ServiceImpl<ProcessDefinitionMapper, BpmProcessDefinitionEntity> {

	@Resource
	protected ModelService modelService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	protected RuntimeService runtimeService;
	@Resource
	protected ManagementService managementService;
	@Resource
	protected HistoryService historyService;
	@Resource
	protected ProcessDefinitionMapper processDefinitionMapper;

	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String category = (String) params.get("category");
		String suspensionState = (String) params.get("suspensionState");
		String name = (String) params.get("name");
		String modelKey = (String) params.get("modelKey");
		Page<BpmProcessDefinitionEntity> page = processDefinitionMapper.findPage(	new Query<BpmProcessDefinitionEntity>(params).getPage(),
				StrUtil.nullToDefault(name,""),
				StrUtil.nullToDefault(modelKey,""),
				StrUtil.nullToDefault(category,""),
				StrUtil.nullToDefault(suspensionState,""),
				(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
//		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
//				.latestVersion().orderByProcessDefinitionKey().asc();
//
//		if (ToolUtil.isNotEmpty(category)){
//			processDefinitionQuery.processDefinitionCategory(category);
//		}
//
//		if (ToolUtil.isNotEmpty(suspensionState) && suspensionState.equals("1")){
//			processDefinitionQuery.active();
//		}
//
//		if (ToolUtil.isNotEmpty(suspensionState) && suspensionState.equals("2")){
//			processDefinitionQuery.suspended();
//		}
//
//		long count = processDefinitionQuery.count();
//		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(Integer.parseInt((String) params.get("page"))-1,
//				Integer.parseInt((String) params.get("limit")));
//
//		List<ProcdefEntity> dataList = new ArrayList<>();
//		for (ProcessDefinition processDefinition : processDefinitionList) {
//			String deploymentId = processDefinition.getDeploymentId();
//			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
//
//			ProcdefEntity reProcDef = new ProcdefEntity((ProcessDefinitionEntityImpl) processDefinition);
//
//			reProcDef.setDeploymentTime(deployment.getDeploymentTime());
//			//reProcDef.setSuspensionState(repositoryService.isProcessDefinitionSuspended(reProcDef.getId())?2:1);
//			dataList.add(reProcDef);
//		}
//		return new PageUtil(dataList,count,50,1);
	}


	/**
	 * 删除流程定义
	 * @param deploymentId
	 * @return
	 */
	public boolean deleteDeployment(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
		return true;
	}


	public BpmProcessDefinitionEntity getById(String deploymentId){
		return this.processDefinitionMapper.getById(deploymentId);
	}

	public ResponseData getDeploymentXml(String deploymentId){
		BpmProcessDefinitionEntity bpmProcessDefinition = this.processDefinitionMapper.getById(deploymentId);
		if(ToolUtil.isNotEmpty(bpmProcessDefinition)){
			InputStream inputStream = null;
			try{
				inputStream = repositoryService.getResourceAsStream(bpmProcessDefinition.getDeploymentId(),
						bpmProcessDefinition.getResourceName());
				String depXml = IoUtil.read(inputStream, CharsetUtil.UTF_8);
				return  ResponseData.success(ResponseData.DEFAULT_SUCCESS_MSG,depXml);
			}catch (Exception e){
				log.error("获取流异常!",e);
			}finally {
				if(inputStream!=null){
					IoUtil.close(inputStream);
				}
			}
			return ResponseData.error();
		}else{
			return ResponseData.error();
		}
	}

	public void downloadXml(HttpServletResponse response, HttpServletRequest request, String deploymentId) {
		BpmProcessDefinitionEntity bpmProcessDefinition = this.processDefinitionMapper.getById(deploymentId);
		if(ToolUtil.isNotEmpty(bpmProcessDefinition)) {
			try {
				InputStream inputStream = repositoryService.getResourceAsStream(bpmProcessDefinition.getDeploymentId(),
						bpmProcessDefinition.getResourceName());

				byte[] data = IoUtil.readBytes(inputStream);
				response.reset();
				String fileName = bpmProcessDefinition.getResourceName();
				//浏览器设置
				String userAgent = request.getHeader("User-Agent");
				if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
					//IE浏览器处理
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else {
					// 非IE浏览器的处理：
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				//下载的文件携带这个名称
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				response.addHeader("Content-Length", "" + data.length);
				response.setContentType("application/octet-stream; charset=UTF-8");
				IOUtils.write(data, response.getOutputStream());
			}catch (Exception e){
				log.error("获取流异常!",e);
			}
		}else {
			log.error("无定义信息!");
		}
	}


	/**
	 * 流程定义的挂起与激活
	 * @param deploymentId
	 * @param suspensionState
	 * @return
	 */
	public boolean suspendOrActivateProcessDefinitionById(String deploymentId,int suspensionState) {
		if (suspensionState == 1){
			repositoryService.suspendProcessDefinitionById(deploymentId, true, null);
		}else {
			repositoryService.activateProcessDefinitionById(deploymentId, true, null);
		}
		return true;
	}


	@Transactional(readOnly = false)
	public ResponseData addDeploy(String modelId, String category) {
		String message = "";
		Model model = modelService.getModel(modelId);
		byte[] bpmnBytes = modelService.getBpmnXML(model);

		String processName = model.getName();
		if (!StrUtil.endWith(processName, ".bpmn20.xml")){
			processName += ".bpmn20.xml";
		}
		//TODO 添加隔离信息 先定死测试
		String tenantId = "system";
		Deployment deployment = repositoryService.createDeployment()
				.addBytes(processName, bpmnBytes)
				.name(model.getName())
				.key(model.getKey())
				.tenantId(tenantId)
				.deploy();

		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId()).list();

		// 设置流程分类
		for (ProcessDefinition processDefinition : list) {
			if(ToolUtil.isNotEmpty(category)) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
			}
		}

		if (list.size() == 0){
			message = "部署失败，没有流程。";
			return ResponseData.error(message);
		}else{
			return ResponseData.success();
		}
	}
}
