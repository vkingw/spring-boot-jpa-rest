package com.vincent.example.authorization.Service;

import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.ExampleUserDetails;
import com.vincent.example.authorization.model.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Created : vincent
 * Date : 2017/8/18 下午5:35
 * Email : wangxiao@wafersystems.com
 */
public class ExampleAuthenticationUserDetailsService implements
  AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  private static final Logger logger = LoggerFactory.getLogger(ExampleAuthenticationUserDetailsService.class);

  private final TokenManager tokenManager;

  @Autowired
  public ExampleAuthenticationUserDetailsService(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws
    UsernameNotFoundException {
    String principal = (String) token.getPrincipal();
    TokenModel tokenModel = null;
    try {
      tokenModel = tokenManager.getToken(principal);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    if (tokenModel != null && tokenManager.checkToken(tokenModel)) {
      return new ExampleUserDetails(tokenModel);
    } else {
      throw new UsernameNotFoundException("NULL");
    }
  }
}
