package com.vincent.example.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public FastJsonHttpMessageConverter fastJsonHttpMessageConverters() {
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(
      SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,
      SerializerFeature.WriteNullListAsEmpty
    );
    fastConverter.setFastJsonConfig(fastJsonConfig);

    return fastConverter;
  }
}
