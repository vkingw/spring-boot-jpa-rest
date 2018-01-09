package com.vincent.example.authorization.resolvers;

import com.vincent.example.config.Constants;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created : vincent
 * Date : 2017/11/21 下午4:15
 * Email : alfa.king+git@gmail.com
 */
public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

  private Locale customLocale;

  public Locale resolveLocale(HttpServletRequest request) {
    return customLocale;
  }

  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    String lang = request.getHeader(Constants.LANG);
    if (null != lang) {
      String[] info = lang.split("_");
      if (info.length == 2) {
        customLocale = new Locale(info[0], info[1]);
      } else {
        customLocale = new Locale(lang);
      }
    } else {
      customLocale = Locale.SIMPLIFIED_CHINESE;
    }
  }

}
