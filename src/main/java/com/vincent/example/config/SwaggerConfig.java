package com.vincent.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created : vincent
 * Date : 2017/8/2 下午4:37
 * Email : wangxiao@wafersystems.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.vincent.example.controller"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Spring Boot中使用Swagger2构建RESTful APIs")
      .description("构建Controller RESTful APIs")
      .termsOfServiceUrl("https://github.com/vkingw")
      .contact(new Contact("Vincent", "https://github.com/vkingw", "wangxiao@wafersystems.com"))
      .version("1.0")
      .build();
  }
}
