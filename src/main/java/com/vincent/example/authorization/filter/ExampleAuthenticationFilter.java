package com.vincent.example.authorization.filter;

import com.vincent.example.config.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created : vincent
 * Date : 2017/8/18 下午5:30
 * Email : wangxiao@wafersystems.com
 */
public class ExampleAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private static final String SSO_CREDENTIALS = "N/A";

  public ExampleAuthenticationFilter(AuthenticationManager authenticationManager) {
    setAuthenticationManager(authenticationManager);
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(Constants.AUTHORIZATION);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return SSO_CREDENTIALS;
  }
}
