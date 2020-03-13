package com.nelsonaguiar.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.nelsonaguiar.repository.one", entityManagerFactoryRef="userEntityManager", transactionManagerRef="userTransactionManager")
@EnableTransactionManagement
@EnableAutoConfiguration
public class DataBaseOneConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties("application.datasource.one")
	public DataSourceProperties getPropsOne() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("application.datasource.one.configuration")
	public DataSource dataSourceOne() {
		return getPropsOne().initializeDataSourceBuilder().type(BasicDataSource.class).build();
	}

	@Bean(name = "userEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerDBOne(EntityManagerFactoryBuilder emf) {
		LocalContainerEntityManagerFactoryBean lcem = emf.dataSource(dataSourceOne()).packages("com.nelsonaguiar.bean").build();
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
		lcem.setPersistenceUnitName("PersistenceUnitOne");
		lcem.setJpaPropertyMap(properties);
		return lcem;
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager userTransactionManager(
	        final @Qualifier("userEntityManager") LocalContainerEntityManagerFactoryBean userEntityManagerFactory) {
	    return new JpaTransactionManager(userEntityManagerFactory.getObject());
	}
}
