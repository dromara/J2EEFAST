package com.j2eefast.flowable.bpm.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.ui.common.service.exception.InternalServerErrorException;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class FlowableBeanConfig {

	protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";
	@Bean
	public FlowableModelerAppProperties flowableModelerAppProperties() {
		FlowableModelerAppProperties flowableModelerAppProperties = new FlowableModelerAppProperties();
		return flowableModelerAppProperties;
	}

	@Bean
	public Liquibase liquibase(@Qualifier("flowableSourcePrimary") DataSource dataSource) {
		log.info("Configuring Liquibase");

		Liquibase liquibase = null;
		try {
			DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
			database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
			database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());

			liquibase = new Liquibase("META-INF/liquibase/flowable-modeler-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update("flowable");
			return liquibase;
		} catch (Exception e) {
			throw new InternalServerErrorException("Error creating liquibase database", e);
		} finally {
			closeDatabase(liquibase);
		}
	}

	private void closeDatabase(Liquibase liquibase) {
		if (liquibase != null) {
			Database database = liquibase.getDatabase();
			if (database != null) {
				try {
					database.close();
				} catch (DatabaseException e) {
					log.error("Error closing database", e);
				}
			}
		}
	}
}
