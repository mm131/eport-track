package com.njeport.track.auth.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author kongming
 * @date 2020/02/07
 */
@Configuration
@MapperScan(basePackages = "com.njeport.**.dao.mapper.track",sqlSessionFactoryRef = "trackSqlSessionFactory")
public class TrackDataSourceConfig extends AbstractDataSourceConfig {

    /**
     * MyBatis Mapper 地址
     */
    @Override
    public String getMapperLocations() {
        return "classpath*:mybatis/mapper/track/**/*.xml";
    }

    /**
     * 构建 数据源
     */
    @Primary
    @Bean(name = "trackDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.track")
    @Override
    public DataSource dataSource() {
        return super.dataSource();
    }

    /**
     * 构建 SqlSessionFactory
     */
    @Primary
    @Bean(name = "trackSqlSessionFactory")
    @Override
    public SqlSessionFactory SessionFactory() throws Exception {
        return super.SessionFactory();
    }

    /**
     * 构建 TransactionManager
     */
    @Primary
    @Bean(name = "trackTransactionManager")
    @Override
    public DataSourceTransactionManager transactionManager() throws Exception {
        return super.transactionManager();
    }
}
