package com.vincent.example.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenModel {

  //用户id
  private long userId;

  //name
  private String userName;

  //随机生成的token
  private String token;

}
