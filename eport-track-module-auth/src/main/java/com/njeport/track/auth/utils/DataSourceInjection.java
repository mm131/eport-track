package com.njeport.track.auth.utils;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author kongming
 * @date 2020/02/07
 */
@Component
public class DataSourceInjection {
    @Value("${spring.datasource.properties.initial-size}")
    private int initialSize;

    @Value("${spring.datasource.properties.max-active}")
    private int maxActive;

    @Value("${spring.datasource.properties.min-idle}")
    private int minIdle;

    @Value("${spring.datasource.properties.max-wait}")
    private int maxWait;

    @Value("${spring.datasource.properties.time-between-eviction-runs-millis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.properties.min-evictable-idle-time-millis}")
    private long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.properties.validation-query}")
    private String validQuery;

    @Value("${spring.datasource.properties.test-while-idle}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.properties.test-on-borrow}")
    private Boolean testOnBorrow;

    @Value("${spring.datasource.properties.test-on-return}")
    private Boolean testOnReturn;

    public void fillProperties(DruidDataSource dataSource) {
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setProxyFilters(Lists.newArrayList(statFilter(), wallFilter(), logFilter()));
    }

    @Bean
    public StatFilter statFilter() {
        return new StatFilter();
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);    //允许一次执行多条语句
        config.setNoneBaseStatementAllow(true); //允许非基本语句的其他语句
        wallFilter.setConfig(config);
        return wallFilter;
    }

    @Bean
    public Log4j2Filter logFilter() {
        return new Log4j2Filter();
    }
}
