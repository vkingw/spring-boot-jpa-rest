package com.vincent.example.controller;

import com.vincent.example.authorization.annotation.CurrentUser;
import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.TokenModel;
import com.vincent.example.exception.ControllerException;
import com.vincent.example.mapper.LoginModel;
import com.vincent.example.model.User;
import com.vincent.example.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Locale;

import static com.vincent.example.config.Constants.AUTHORIZATION;

/**
 * Created : vincent
 * Date : 2017/8/3 下午12:40
 * Email : wangxiao@wafersystems.com
 */
@Controller
@RequestMapping("${spring.data.rest.base-path}")
@Api(value = "登录和注销", tags = "登录和注销API", description = "系统登录和注销")
@Slf4j
public class TokenController {

  private final MessageSource messageSource;

  private final UserRepository userRepository;

  private final TokenManager tokenManager;

  @Autowired
  public TokenController(MessageSource messageSource, UserRepository userRepository, TokenManager tokenManager) {
    this.messageSource = messageSource;
    this.userRepository = userRepository;
    this.tokenManager = tokenManager;
  }


  @PostMapping(path = "/login")
  @ApiOperation(value = "登录")
  public ResponseEntity<TokenModel> login(@RequestBody @Valid LoginModel loginModel) throws ControllerException {
    Locale locale = LocaleContextHolder.getLocale();

    User user = userRepository.findUserByUserId(loginModel.getUserId());
    if (user == null ||  //未注册
      !checkPassword(user, loginModel.getPassword())) {  //密码错误
      //提示用户名或密码错误
      throw new ControllerException(HttpStatus.NOT_FOUND, messageSource.getMessage("login.error", null, locale));
    }
    //生成一个token，保存用户登录状态
    TokenModel tokenModel;
    try {
      tokenModel = tokenManager.createToken(user);
    } catch (Exception e) {
      log.error("Create Token Error.", e);
      throw new ControllerException(HttpStatus.EXPECTATION_FAILED, messageSource.getMessage("login.token.create" +
        ".error", null, locale));
    }
    return ResponseEntity.ok(tokenModel);
  }

  @DeleteMapping(path = "/logout")
  @ApiOperation(value = "退出登录")
  @ApiImplicitParams({
    @ApiImplicitParam(name = AUTHORIZATION, value = AUTHORIZATION, required = true, dataType =
      "string", paramType = "header")
  })
  public ResponseEntity logout(@CurrentUser User user) {
    tokenManager.deleteToken(user.getId());
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  private boolean checkPassword(User user, String password) {
    return BCrypt.checkpw(password, user.getPassword());
  }
}
