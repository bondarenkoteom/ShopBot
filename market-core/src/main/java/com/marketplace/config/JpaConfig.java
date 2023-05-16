package com.marketplace.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.marketplace.database.jpa")
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(
                "jdbc:postgresql://localhost:5432/centaur",
                "postgres",
                "Kill"
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.marketplace");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

//    private Properties jpaProperties() {
//        Properties extraProperties = new Properties();
//        extraProperties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        extraProperties.put("spring.jpa.properties.format_sql", true);
//        extraProperties.put("spring.jpa.hibernate.ddl-auto", "update");
//        extraProperties.put("spring.jpa.show-sql", "true");
//        return extraProperties;
//    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();
        extraProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        extraProperties.put("hibernate.format_sql", true);
        extraProperties.put("hibernate.hbm2ddl.auto", "update");
        extraProperties.put("hibernate.show_sql", true);
        return extraProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

}
