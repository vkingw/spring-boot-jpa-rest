package com.vincent.example.config;

/**
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
public class Constants {

  /**
   * 存储当前登录用户id的字段名
   */
  public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

  /**
   * token有效期（小时）
   */
  public static final int TOKEN_EXPIRES_HOUR = 72;

  /**
   * 存放Authorization的header字段
   */
  public static final String AUTHORIZATION = "X-Auth-Token";

  /**
   * jwt
   */
  public static final String JWT_ID = "jwt";
  public static final String JWT_SECRET = "hong1mu2zhi3ruan4jian5";
  public static final String JWT_JIAN_SHU = "noone";
  public static final int JWT_TTL = 60 * 60 * 1000;  //millisecond
  public static final long JWT_TTL_LONG = 60 * 60 * 1000;  //millisecond
  public static final int JWT_REFRESH_INTERVAL = 55 * 60 * 1000;  //millisecond
  public static final int JWT_REFRESH_TTL = 12 * 60 * 60 * 1000;  //millisecond


}
