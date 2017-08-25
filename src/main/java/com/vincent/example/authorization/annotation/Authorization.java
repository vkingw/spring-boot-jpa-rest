package com.vincent.example.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回401错误
 *
 * @see com.vincent.example.authorization.interceptor.AuthorizationInterceptor
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
