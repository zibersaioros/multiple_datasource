package com.rs.multiple_datasource.biz_a.config;

import com.rs.multiple_datasource.biz_a.persistence.BizAMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackageClasses= BizAMapper.class)
public class BizAConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.biza-master")
    public DataSource bizAMasterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.biza-slave")
    public DataSource bizASlaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier("bizAMasterDataSource") DataSource master
        , @Qualifier("bizASlaveDataSource") DataSource slave){
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", master);
        dataSourceMap.put("slave", slave);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(master);

        return routingDataSource;
    }

    @Bean
    public DataSource lazyRoutingDataSource(@Qualifier("routingDataSource") DataSource routingDataSource){
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public SqlSessionFactory SqlSessionFactory(@Qualifier("lazyRoutingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory.getObject();
    }

    @Bean
    @Primary
    public PlatformTransactionManager bizATransactionManager(@Qualifier("lazyRoutingDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
