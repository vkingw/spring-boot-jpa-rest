package com.vincent.example.authorization.interceptor;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vincent.example.config.Constants.LANG;

/**
 * Created : vincent
 * Date : 2017/11/21 下午5:50
 * Email : alfa.king+git@gmail.com
 */
public class LocaleInterceptor extends LocaleChangeInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws ServletException {
    String newLocale = request.getHeader(LANG);
    if (newLocale != null) {
      if (checkHttpMethod(request.getMethod())) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver == null) {
          throw new IllegalStateException(
            "No LocaleResolver found: not in a DispatcherServlet request?");
        }
        try {
          localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
        } catch (IllegalArgumentException ex) {
          if (isIgnoreInvalidLocale()) {
            logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
          } else {
            throw ex;
          }
        }
      }
    }
    // Proceed in any case.
    return true;
  }

  private boolean checkHttpMethod(String currentMethod) {
    String[] configuredMethods = getHttpMethods();
    if (ObjectUtils.isEmpty(configuredMethods)) {
      return true;
    }
    for (String configuredMethod : configuredMethods) {
      if (configuredMethod.equalsIgnoreCase(currentMethod)) {
        return true;
      }
    }
    return false;
  }
}
