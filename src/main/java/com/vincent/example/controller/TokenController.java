package com.vincent.example.controller;

import com.vincent.example.authorization.annotation.Authorization;
import com.vincent.example.authorization.annotation.CurrentUser;
import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.TokenModel;
import com.vincent.example.model.ErrorResultModel;
import com.vincent.example.model.User;
import com.vincent.example.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created : vincent
 * Date : 2017/8/3 下午12:40
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestController
@RequestMapping("/tokens")
public class TokenController {

  private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

  private final MessageSource messageSource;

  private final UserRepository userRepository;

  private final TokenManager tokenManager;

  @Autowired
  public TokenController(MessageSource messageSource, UserRepository userRepository, TokenManager tokenManager) {
    this.messageSource = messageSource;
    this.userRepository = userRepository;
    this.tokenManager = tokenManager;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "登录")
  public ResponseEntity<TokenModel> login(@RequestParam String name, @RequestParam String password) {
    Assert.notNull(name, "name can not be empty");
    Assert.notNull(password, "password can not be empty");

    User user = userRepository.findUserByName(name);
    if (user == null ||  //未注册
      !user.getPassword().equals(password)) {  //密码错误
      //提示用户名或密码错误
      Locale locale = LocaleContextHolder.getLocale();
      return new ResponseEntity(ErrorResultModel.error(HttpStatus.NOT_FOUND,
        messageSource.getMessage("login.error", null, locale)), HttpStatus.NOT_FOUND);
    }
    //生成一个token，保存用户登录状态
    TokenModel tokenModel;
    try {
      tokenModel = tokenManager.createToken(user);
    } catch (Exception e) {
      logger.error("Create Token Error.", e);
      Locale locale = LocaleContextHolder.getLocale();
      return new ResponseEntity(ErrorResultModel.error(HttpStatus.EXPECTATION_FAILED,
        messageSource.getMessage("login.token.create.error", null, locale)), HttpStatus.EXPECTATION_FAILED);
    }
    return ResponseEntity.ok(tokenModel);
  }

  @DeleteMapping
  @Authorization
  @ApiOperation(value = "退出登录")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType =
      "string", paramType = "header"),
  })
  public @ResponseBody
  ResponseEntity<?> logout(@CurrentUser User user) {
    tokenManager.deleteToken(user.getId());
    return ResponseEntity.ok("");
  }

}
