package com.vincent.example.config;

import com.vincent.example.authorization.interceptor.LocaleInterceptor;
import com.vincent.example.authorization.resolvers.CurrentUserMethodArgumentResolver;
import com.vincent.example.authorization.resolvers.CustomAcceptHeaderLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created : vincent
 * Date : 2017/8/2 下午4:46
 * Email : wangxiao@wafersystems.com
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
    return new CurrentUserMethodArgumentResolver();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(currentUserMethodArgumentResolver());
    super.addArgumentResolvers(argumentResolvers);
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new CustomAcceptHeaderLocaleResolver();
  }

  @Bean
  public LocaleInterceptor localeInterceptor() {
    LocaleInterceptor lci = new LocaleInterceptor();
    lci.setParamName(Constants.LANG);
    return lci;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeInterceptor());
  }

}
