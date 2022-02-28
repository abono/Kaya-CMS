package com.aranya.kayacms.config;

import com.aranya.kayacms.db.PooledDelegatingDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    // dataSource.setDriverClassName(driverClass);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    dataSource.setMinIdle(1);
    dataSource.setMaxIdle(5);
    dataSource.setMaxOpenPreparedStatements(100);

    return new PooledDelegatingDataSource(dataSource);
  }
}
