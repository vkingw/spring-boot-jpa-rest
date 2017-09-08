package com.vincent.example.config;

import com.vincent.example.authorization.interceptor.AuthorizationInterceptor;
import com.vincent.example.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
  @Autowired
  public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor, CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver) {
    this.authorizationInterceptor = authorizationInterceptor;
    this.currentUserMethodArgumentResolver = currentUserMethodArgumentResolver;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  private final AuthorizationInterceptor authorizationInterceptor;

  private final CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authorizationInterceptor);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(currentUserMethodArgumentResolver);
  }

}
