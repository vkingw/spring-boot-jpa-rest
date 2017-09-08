package com.vincent.example.config;

import com.vincent.example.model.Employee;
import com.vincent.example.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created : vincent
 * Date : 2017/8/1 上午10:35
 * Email : wangxiao@wafersystems.com
 */
@Configuration
public class RepositoryConfiguration {

  @Bean
  EmployeeHandler employeeHandler() {
    return new EmployeeHandler();
  }

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return new RepositoryRestConfigurerAdapter() {
      @Override
      public void configureRepositoryRestConfiguration(
        RepositoryRestConfiguration config) {
        config.exposeIdsFor(Employee.class);
      }
    };
  }
}
