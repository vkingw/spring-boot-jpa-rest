package com.vincent.example.authorization.filter;

import com.vincent.example.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_NORMAL;
import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_ROOT;

/**
 * Created : vincent
 * Date : 2017/8/18 下午5:30
 * Email : wangxiao@wafersystems.com
 */
@Slf4j
public class ExampleAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private static final String SSO_CREDENTIALS = "N/A";

  public ExampleAuthenticationFilter(AuthenticationManager authenticationManager) {
    setAuthenticationManager(authenticationManager);
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    if (request.getServletPath().startsWith(SOCKET_PUSH_PATH_ROOT + SOCKET_PUSH_PATH_NORMAL)) {
      try {
        String[] parameterValues = request.getParameterValues(Constants.AUTHORIZATION);
        if (parameterValues.length > 0) {
          return parameterValues[0];
        } else {
          if (log.isDebugEnabled()) {
            log.debug("socket not get token", new Object[]{parameterValues});
          }
        }
      } catch (Exception e) {
        if (log.isDebugEnabled()) {
          log.debug("socket token error:", (Object[]) request.getParameterValues(Constants.AUTHORIZATION));
          log.debug("socket token error:", (Object[]) e.getStackTrace());
        }
      }
    } else {
      return request.getHeader(Constants.AUTHORIZATION);
    }
    return null;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return SSO_CREDENTIALS;
  }
}
