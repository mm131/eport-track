package com.njeport.track.auth.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.njeport.track.auth.dao.typehandler.IntValueEnumTypeHandler;
import com.njeport.track.auth.utils.DataSourceInjection;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author kongming
 * @date 2020/02/07
 */
public abstract class AbstractDataSourceConfig {

    @Resource
    private DataSourceInjection dataSourceInjection;

    /**
     * MyBatis Mapper 地址
     */
    public abstract String getMapperLocations();

    /**
     * 数据源
     */
    public DataSource dataSource() {
        DruidDataSource dataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        dataSourceInjection.fillProperties(dataSource);
        return dataSource;
    }

    /**
     * 构建 SqlSessionFactory
     */
    public SqlSessionFactory SessionFactory() throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources(getMapperLocations());
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.njeport.**.dao.meta");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultEnumTypeHandler(IntValueEnumTypeHandler.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 构建 TransactionManager
     */
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }
}
