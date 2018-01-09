package com.vincent.example.config;

/**
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
public class Constants {

  /**
   * token有效期（小时）
   */
  public static final int TOKEN_EXPIRES_HOUR = 72;

  /**
   * 存放Authorization的header字段
   */
  public static final String AUTHORIZATION = "X-Auth-Token";


  public static final String LANG = "lang";

  /**
   * jwt
   */
  public static final String JWT_SECRET = "sdafsadfasdf";
  public static final String JWT_JIAN_SHU = "noone";
  public static final long JWT_TTL_LONG = 60 * 60 * 1000 * 24 * 30;  //millisecond

  /**
   * socket session user id
   */
  public static final String SOCKET_SESSION_USER_ID = "SOCKET_SESSION_USER_ID";

  public static final String SOCKET_PUSH_PATH_ROOT = "/sockjs";
  public static final String SOCKET_PUSH_PATH_NORMAL = "/socket-push";
  public static final String SOCKET_PUSH_PATH_LARGE_SCREEN = "/large_screen_push";


}
