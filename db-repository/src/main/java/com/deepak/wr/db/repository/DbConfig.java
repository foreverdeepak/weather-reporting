package com.deepak.wr.db.repository;

import com.deepak.wr.db.repository.impl.WeatherRepositoryImpl;
import com.deepak.wr.platform.PlatformConfig;
import com.deepak.wr.platform.conf.Config;
import com.deepak.wr.platform.conf.Rdbbms;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Import({PlatformConfig.class})
public class DbConfig {

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(Config config) {
        Rdbbms rdb = config.getRdbbmsById("weather-reporting-db");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName("com.mysql.jdbc.Driver");

        hikariConfig.setJdbcUrl("jdbc:mysql://" + rdb.getHost() + ":" + rdb.getPort() + "/" + rdb.getDbName());
        hikariConfig.setUsername(rdb.getUsername());
        hikariConfig.setPassword(rdb.getPassword());
        hikariConfig.setMaximumPoolSize(rdb.getMaxPoolSize());
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public WeatherRepository weatherRepository(JdbcTemplate jdbcTemplate) {
        return new WeatherRepositoryImpl(jdbcTemplate);
    }
}
