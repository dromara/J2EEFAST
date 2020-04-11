package com.j2eefast.common.core.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.j2eefast.common.core.thread.Threads;
import com.j2eefast.common.core.utils.SpringUtil;

/**
 * 异步管理器
 * @author zhouzhou
 * @date 2017-03-12 16:29
 */
public class AsyncManager {
	
	/**
     * 操作延迟10毫秒
     */
    private final int 					OPERATE_DELAY_TIME 								= 10;
    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService 	executor 										= SpringUtil.getBean("scheduledExecutorService");
    /**
     * 单例模式
     */
    private static AsyncManager 		ASYNCMANAGER 									= new AsyncManager();

    public static AsyncManager me(){
        return ASYNCMANAGER;
    }

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    public void execute(TimerTask task)
    {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
