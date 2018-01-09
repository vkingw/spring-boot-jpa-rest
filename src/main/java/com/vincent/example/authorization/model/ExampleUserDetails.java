package com.vincent.example.authorization.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created : vincent
 * Date : 2017/8/18 下午5:37
 * Email : alfa.king+git@gmail.com
 */
public class ExampleUserDetails implements UserDetails {

  private TokenModel tokenModel;

  public ExampleUserDetails(TokenModel tokenModel) {
    this.tokenModel = tokenModel;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return tokenModel.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
