package com.vincent.example.authorization.manager.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincent.example.authorization.manager.TokenManager;
import com.vincent.example.authorization.model.TokenModel;
import com.vincent.example.config.Constants;
import com.vincent.example.jwt.JwtUtil;
import com.vincent.example.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @see TokenManager
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : alfa.king+git@gmail.com
 */
@Component
public class RedisTokenManager implements TokenManager {

  private RedisTemplate<Long, String> redis;

  @Autowired
  public void setRedis(@Qualifier("stringRedisTemplate") RedisTemplate redis) {
    this.redis = redis;
    //泛型设置成Long后必须更改对应的序列化方案
    redis.setKeySerializer(new JdkSerializationRedisSerializer());
  }

  public TokenModel createToken(User user) throws Exception {
    JwtUtil jwtUtil = new JwtUtil();
    String token = jwtUtil.createJWT(user.getUserId(), JwtUtil.generalSubject(user), Constants.JWT_TTL_LONG);
    TokenModel model = new TokenModel(user.getId(), user.getUserId(), user.getName(), token);
    //存储到redis并设置过期时间
    redis.boundValueOps(user.getId()).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
    return model;
  }

  public TokenModel getToken(String authentication) {
    if (authentication == null || authentication.length() == 0) {
      return null;
    }
    JwtUtil jwtUtil = new JwtUtil();
    try {
      Claims claims = jwtUtil.parseJWT(authentication);
      ObjectMapper mapper = new ObjectMapper();
      User user = mapper.readValue(claims.getSubject(), User.class);
      return new TokenModel(user.getId(), user.getUserId(), user.getName(), authentication);
    } catch (Exception e) {
      return null;
    }
  }

  public boolean checkToken(TokenModel model) {
    if (model == null) {
      return false;
    }
    String token = redis.boundValueOps(model.getId()).get();
    if (token == null || !token.equals(model.getToken())) {
      return false;
    }
    //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
    redis.boundValueOps(model.getId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
    return true;
  }

  public void deleteToken(long userId) {
    redis.delete(userId);
  }
}
