package com.fast.framework.shiro;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 
 * @author zhouzhou
 * @date 2020-03-12 20:56
 */
public class ShiroSessionListener implements SessionListener {

	/**
	 * 统计在线人数 juc包下线程安全自增
	 */
	private final AtomicInteger sessionCount = new AtomicInteger(0);

	/**
	 * 会话创建时触发
	 * 
	 * @param session
	 */
	@Override
	public void onStart(Session session) {
		System.out.println("-------------------------会话创建:session:" + session.getId());
		// 会话创建，在线人数加一
		sessionCount.incrementAndGet();
	}

	/**
	 * 退出会话时触发
	 * 
	 * @param session
	 */
	@Override
	public void onStop(Session session) {
		System.out.println("-------------------------会话退出:session:" + session.getId());
		// 会话退出,在线人数减一
		sessionCount.decrementAndGet();
	}

	/**
	 * 会话过期时触发
	 * 
	 * @param session
	 */
	@Override
	public void onExpiration(Session session) {
		System.out.println("-------------------------会话过期,:session:" + session.getId());
//		redisUtil.set("sys:session:" + session.getId(), "00001", redisUtil.MINUTE);
		// 会话过期,在线人数减一
		sessionCount.decrementAndGet();
	}

	/**
	 * 获取在线人数使用
	 * 
	 * @return
	 */
	public AtomicInteger getSessionCount() {
		return sessionCount;
	}

}
