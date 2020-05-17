package com.j2eefast.flowable.bpm.service;

import com.j2eefast.flowable.bpm.mapper.IHisFlowableActinstMapper;
import com.j2eefast.flowable.bpm.mapper.IRunFlowableActinstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-28 18:08
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class FlowableActinstService {

	@Autowired
	private IHisFlowableActinstMapper hisFlowableActinstMapper;
	@Autowired
	private IRunFlowableActinstMapper runFlowableActinstMapper;

	public  void deleteHisActinstsByIds(List<String> runActivityIds ){
		this.hisFlowableActinstMapper.deleteHisActinstsByIds(runActivityIds);
	}

	public  void deleteRunActinstsByIds(List<String> runActivityIds ){
		this.runFlowableActinstMapper.deleteRunActinstsByIds(runActivityIds);
	}


	public String getBpmActivityId(String userId,
							String processInstanceId){
		return this.hisFlowableActinstMapper.getActivityId(userId,processInstanceId);
	}

}
