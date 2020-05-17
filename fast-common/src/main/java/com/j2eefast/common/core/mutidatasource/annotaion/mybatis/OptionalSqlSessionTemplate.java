package com.j2eefast.common.core.mutidatasource.annotaion.mybatis;

import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.*;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import static java.lang.reflect.Proxy.newProxyInstance;
import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.mybatis.spring.SqlSessionUtils.*;
/**
 * <p>DataSourceContextHolder 动态获取SqlSessionFactory</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 14:34
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class OptionalSqlSessionTemplate  extends SqlSessionTemplate {

	private final SqlSessionFactory sqlSessionFactory;
	private final ExecutorType executorType;
	private final SqlSession sqlSessionProxy;
	private final PersistenceExceptionTranslator exceptionTranslator;

	private Map<Object, SqlSessionFactory> targetSqlSessionFactorys;

	public OptionalSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, Map<Object, SqlSessionFactory> targetSqlSessionFactorys) {
		super(sqlSessionFactory);
		this.targetSqlSessionFactorys = targetSqlSessionFactorys;

		//原构造函数方法
		this.sqlSessionFactory = sqlSessionFactory;
		this.executorType = sqlSessionFactory.getConfiguration().getDefaultExecutorType();
		this.exceptionTranslator = new MyBatisExceptionTranslator(
				sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
		this.sqlSessionProxy = (SqlSession) newProxyInstance(
				SqlSessionFactory.class.getClassLoader(),
				new Class[]{SqlSession.class},
				new SqlSessionInterceptor());
	}

	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		if (targetSqlSessionFactorys == null) {
			throw new IllegalArgumentException("OptionalSqlSessionTemplate初始化时，未初始化targetSqlSessionFactorys！");
		}
		if (DataSourceContextHolder.getDataSourceType() == null) {
			return sqlSessionFactory;
		}
		SqlSessionFactory targetSqlSessionFactory = this.targetSqlSessionFactorys.get(DataSourceContextHolder.getDataSourceType());
		if (targetSqlSessionFactory != null) {
			return targetSqlSessionFactory;
		} else {
			return sqlSessionFactory;
		}
	}

	public ExecutorType getExecutorType() {
		return this.executorType;
	}

	public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
		return this.exceptionTranslator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T selectOne(String statement) {
		return this.sqlSessionProxy.selectOne(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T selectOne(String statement, Object parameter) {
		return this.sqlSessionProxy.selectOne(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.sqlSessionProxy.selectMap(statement, mapKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.sqlSessionProxy.selectMap(statement, parameter, mapKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Cursor<T> selectCursor(String statement) {
		return this.sqlSessionProxy.selectCursor(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter) {
		return this.sqlSessionProxy.selectCursor(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
		return this.sqlSessionProxy.selectCursor(statement, parameter, rowBounds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> List<E> selectList(String statement) {
		return this.sqlSessionProxy.selectList(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.sqlSessionProxy.selectList(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.sqlSessionProxy.selectList(statement, parameter, rowBounds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void select(String statement, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void select(String statement, Object parameter, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int insert(String statement) {
		return this.sqlSessionProxy.insert(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int insert(String statement, Object parameter) {
		return this.sqlSessionProxy.insert(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(String statement) {
		return this.sqlSessionProxy.update(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(String statement, Object parameter) {
		return this.sqlSessionProxy.update(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(String statement) {
		return this.sqlSessionProxy.delete(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(String statement, Object parameter) {
		return this.sqlSessionProxy.delete(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getMapper(Class<T> type) {
		return getConfiguration().getMapper(type, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commit() {
		throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commit(boolean force) {
		throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rollback() {
		throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rollback(boolean force) {
		throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		throw new UnsupportedOperationException("Manual close is not allowed over a Spring managed SqlSession");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearCache() {
		this.sqlSessionProxy.clearCache();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Configuration getConfiguration() {
		return this.sqlSessionFactory.getConfiguration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection getConnection() {
		return this.sqlSessionProxy.getConnection();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 1.0.2
	 */
	@Override
	public List<BatchResult> flushStatements() {
		return this.sqlSessionProxy.flushStatements();
	}

	/**
	 * copy from super class org.mybatis.spring.SqlSessionTemplate
	 *
	 * @author Putthiphong Boonphong
	 * @author Hunter Presnall
	 * @author Eduardo Macarron
	 */
	private class SqlSessionInterceptor implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			SqlSession sqlSession = getSqlSession(
					OptionalSqlSessionTemplate.this.getSqlSessionFactory(),
					OptionalSqlSessionTemplate.this.executorType,
					OptionalSqlSessionTemplate.this.exceptionTranslator);
			try {
				Object result = method.invoke(sqlSession, args);
				if (!isSqlSessionTransactional(sqlSession, OptionalSqlSessionTemplate.this.getSqlSessionFactory())) {
					// force commit even on non-dirty sessions because some databases require
					// a commit/rollback before calling close()
					sqlSession.commit(true);
				}
				return result;
			} catch (Throwable t) {
				Throwable unwrapped = unwrapThrowable(t);
				if (OptionalSqlSessionTemplate.this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
					// release the connection to avoid a deadlock if the translator is no loaded. See issue #22
					closeSqlSession(sqlSession, OptionalSqlSessionTemplate.this.getSqlSessionFactory());
					sqlSession = null;
					Throwable translated = OptionalSqlSessionTemplate.this.exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
					if (translated != null) {
						unwrapped = translated;
					}
				}
				throw unwrapped;
			} finally {
				if (sqlSession != null) {
					closeSqlSession(sqlSession, OptionalSqlSessionTemplate.this.getSqlSessionFactory());
				}
			}
		}
	}
}
