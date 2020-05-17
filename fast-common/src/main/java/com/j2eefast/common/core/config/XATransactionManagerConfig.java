package com.j2eefast.common.core.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * <p>JTA 事务配置</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-16 16:29
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Configuration
@EnableTransactionManagement
public class XATransactionManagerConfig {

	@Bean
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	@Bean
	public PlatformTransactionManager transactionManager(UserTransaction userTransaction,
														 TransactionManager transactionManager) {
		return new JtaTransactionManager(userTransaction, transactionManager);
	}
}
