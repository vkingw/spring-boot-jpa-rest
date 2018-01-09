package com.vincent.example.authorization.resolvers;

import com.vincent.example.authorization.annotation.CurrentUser;
import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.TokenModel;
import com.vincent.example.model.User;
import com.vincent.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.vincent.example.config.Constants.AUTHORIZATION;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 *
 * @see com.vincent.example.authorization.annotation.CurrentUser
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : alfa.king+git@gmail.com
 */
@Component
@Slf4j
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenManager tokenManager;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    //如果参数类型是User并且有CurrentUser注解则支持
    if (parameter.getParameterType().isAssignableFrom(User.class) &&
      parameter.hasParameterAnnotation(CurrentUser.class)) {
      return true;
    }
    return false;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest
    webRequest, WebDataBinderFactory binderFactory) throws UsernameNotFoundException {
    //取出鉴权时存入的登录用户Id
    String token = webRequest.getHeader(AUTHORIZATION);
    TokenModel tokenModel = null;
    try {
      tokenModel = tokenManager.getToken(token);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    if (tokenModel != null && tokenManager.checkToken(tokenModel)) {
      return userRepository.findUserByUserId(tokenModel.getUserId());
    } else {
      throw new UsernameNotFoundException("NULL");
    }
  }
}
