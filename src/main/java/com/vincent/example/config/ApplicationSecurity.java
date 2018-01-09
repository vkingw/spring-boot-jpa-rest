package com.vincent.example.config;

import com.vincent.example.authorization.Service.ExampleAuthenticationUserDetailsService;
import com.vincent.example.authorization.filter.ExampleAuthenticationFilter;
import com.vincent.example.authorization.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_LARGE_SCREEN;
import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_ROOT;

/**
 * Created : vincent
 * Date : 2017/8/3 下午12:01
 * Email : alfa.king+git@gmail.com
 */

@Configurable
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  private final TokenManager tokenManager;

  @Value(value = "${custom.variables.swagger2.switch}")
  private boolean openSwagger2;

  @Autowired
  public ApplicationSecurity(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (openSwagger2) {
      http.authorizeRequests().antMatchers("/api/login", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**",
        "/v2/**", SOCKET_PUSH_PATH_ROOT + SOCKET_PUSH_PATH_LARGE_SCREEN + "/**")
        .permitAll()
        .anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().csrf().disable();
    } else {
      http.authorizeRequests().antMatchers("/api/login", SOCKET_PUSH_PATH_ROOT + SOCKET_PUSH_PATH_LARGE_SCREEN + "/**")
        .permitAll()
        .anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().csrf().disable();
    }
    // 认证转换器
    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    http.addFilter(headerAuthenticationFilter());
    http.formLogin().successHandler(authenticationSuccessHandler());
    http.formLogin().failureHandler(authenticationFailureHandler());
  }

  /**
   * 处理未授权.
   *
   * @return AuthenticationEntryPoint
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Bean
  public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        clearAuthenticationAttributes(request);
      }
    };
  }

  @Bean
  public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler() {
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException,
        ServletException {
        super.onAuthenticationFailure(request, response, exception);
      }
    };
  }

  @Bean
  public ExampleAuthenticationFilter headerAuthenticationFilter() throws Exception {
    return new ExampleAuthenticationFilter(authenticationManager());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder builder) {
    builder.authenticationProvider(authenticationProvider());
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(new ExampleAuthenticationUserDetailsService(tokenManager));
    return provider;
  }

}

