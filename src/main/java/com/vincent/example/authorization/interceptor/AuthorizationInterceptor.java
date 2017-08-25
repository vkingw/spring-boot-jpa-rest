package com.vincent.example.authorization.interceptor;

import com.vincent.example.authorization.annotation.Authorization;
import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.TokenModel;
import com.vincent.example.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 * @see com.vincent.example.authorization.annotation.Authorization
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

  private final TokenManager manager;

  @Autowired
  public AuthorizationInterceptor(TokenManager manager) {
    this.manager = manager;
  }

  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    //如果不是映射到方法直接通过
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Method method = handlerMethod.getMethod();
    //从header中得到token
    String authorization = request.getHeader(Constants.AUTHORIZATION);
    //验证token
    TokenModel tokenModel = manager.getToken(authorization);
    if (tokenModel != null && manager.checkToken(tokenModel)) {
      //如果token验证成功，将token对应的用户id存在request中，便于之后注入
      request.setAttribute(Constants.CURRENT_USER_ID, tokenModel.getUserId());
      return true;
    }
    //如果验证token失败，并且方法注明了Authorization，返回401错误
    if (method.getAnnotation(Authorization.class) != null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }
    return true;
  }
}
