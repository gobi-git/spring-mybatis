package com.prohance.springmybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.prohance.springmybatis.dao.dest", sqlSessionTemplateRef  = "SecondarySessionTemplate")
public class DestDatabaseConfig {
	
	@Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    //  @Primary
    public DataSource SecondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SecondarySessionFactory")
    //  @Primary
    public SqlSessionFactory SecondarySessionFactory(@Qualifier("SecondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "SecondaryTransactionManager")
    //   @Primary
    public DataSourceTransactionManager SecondaryTransactionManager(@Qualifier("SecondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SecondarySessionTemplate")
    //   @Primary
    public SqlSessionTemplate SecondarySessionTemplate(@Qualifier("SecondarySessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
