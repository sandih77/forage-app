package com.core.forage_app.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.core.forage_app.repository")
public class JpaConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/forage_app");
        ds.setUsername("postgres");
        ds.setPassword("postgres");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.core.forage_app.entity");

        HibernateJpaVendorAdapter vendor =
                new HibernateJpaVendorAdapter();

        emf.setJpaVendorAdapter(vendor);

        Map<String, Object> props = new HashMap<>();

        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        emf.setJpaPropertyMap(props);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager tx = new JpaTransactionManager();

        tx.setEntityManagerFactory(
                entityManagerFactory().getObject()
        );

        return tx;
    }
}
