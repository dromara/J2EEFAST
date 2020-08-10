package com.j2eefast.framework.config;

import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.quartz.entity.SysJobEntity;
import com.j2eefast.framework.quartz.service.SysJobService;
import com.j2eefast.framework.quartz.utils.ScheduleUtils;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.service.SysModuleService;
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
 * @Package: com.j2eefast.framework.config
 * @author: zhouzhou Emall:18774995071@163.com
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

        //清除
		scheduler.clear();

        /**
         * 检测定时任务
         */
        List<SysJobEntity> sysJobList =  sysJobService.list();
        if(ToolUtil.isNotEmpty(sysJobList)) {
        	for(SysJobEntity sysJob: sysJobList) {
//        		CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, sysJob.getJobId());
//    			// 如果不存在，则创建
//    			if(cronTrigger == null) {
    				ScheduleUtils.createScheduleJob(scheduler, sysJob);
//    			}else{
//    				ScheduleUtils.updateScheduleJob(scheduler, sysJob);
//    			}
        	}
        }
        
    }
}
