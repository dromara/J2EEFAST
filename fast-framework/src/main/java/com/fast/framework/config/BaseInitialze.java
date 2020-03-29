package com.fast.framework.config;

import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.quartz.entity.SysJobEntity;
import com.fast.framework.quartz.service.SysJobService;
import com.fast.framework.quartz.utils.ScheduleUtils;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.service.SysModuleService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @ClassName: BaseInitialze
 * @Package: com.fast.framework.config
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/2/14 18:32
 * @version V1.0
 *
 */
@Component
public class BaseInitialze  implements ApplicationRunner {
    public static final Logger logger = LoggerFactory.getLogger(BaseInitialze.class);

    @Autowired
    private SysModuleService sysModuleService;
	@Qualifier("schedulerFactoryBean")
	@Autowired
	private Scheduler scheduler;
    @Autowired
    private SysJobService sysJobService;
    

    @Override
    public void run(ApplicationArguments args) throws Exception {
    	
    	/**
    	 * 初始化检测模块
    	 */
        List<SysModuleEntity> list = sysModuleService.list();
        for(SysModuleEntity entity: list){
            boolean flag = true;
            try {
                Class.forName(entity.getMainClassName());
            }catch (ClassNotFoundException e){
                flag = false;
            }
            if(!flag){
                if(!entity.getStatus().equals("2")){
                	entity.setStatus("2");
                	sysModuleService.setRoles(entity.getId(), entity.getStatus());
                }
            }else{
                if(!entity.getStatus().equals("0")){
                	entity.setStatus("0");
                    sysModuleService.setRoles(entity.getId(), entity.getStatus());
                }
            }
        }
        
        /**
         * 检测定时任务
         */
        List<SysJobEntity> sysJobList =  sysJobService.list();
        if(ToolUtil.isNotEmpty(sysJobList)) {
        	for(SysJobEntity sysJob: sysJobList) {
        		CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, sysJob.getJobId());
    			// 如果不存在，则创建
    			if (cronTrigger == null) {
    				ScheduleUtils.createScheduleJob(scheduler, sysJob);
    			} else {
    				ScheduleUtils.updateScheduleJob(scheduler, sysJob);
    			}
        	}
        }
        
    }
}
