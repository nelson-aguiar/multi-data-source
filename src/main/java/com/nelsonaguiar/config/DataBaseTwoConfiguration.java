package com.nelsonaguiar.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.nelsonaguiar.repository.two", entityManagerFactoryRef = "userTwoEntityManager", transactionManagerRef = "userTwoTransactionManager")
@EnableTransactionManagement
public class DataBaseTwoConfiguration {

	@Bean
	@ConfigurationProperties("application.datasource.two")
	public DataSourceProperties getPropstwo() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("application.datasource.two.configuration")
	public DataSource dataSourceTwo() {
		return getPropstwo().initializeDataSourceBuilder().type(BasicDataSource.class).build();
	}

	@Bean(name = "userTwoEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerDBTwo(EntityManagerFactoryBuilder emf) {
		LocalContainerEntityManagerFactoryBean lcem = emf.dataSource(dataSourceTwo()).packages("com.nelsonaguiar.bean").build();
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
		lcem.setPersistenceUnitName("PersistenceUnitTwo");
		lcem.setJpaPropertyMap(properties);
		return lcem;
	}

	@Bean
	public PlatformTransactionManager userTwoTransactionManager(
			final @Qualifier("userTwoEntityManager") LocalContainerEntityManagerFactoryBean userEntityManagerFactory) {
		return new JpaTransactionManager(userEntityManagerFactory.getObject());
	}

}
