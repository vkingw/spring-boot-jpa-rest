package com.vincent.example.mapper;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created : vincent
 * Date : 2017/10/11 上午10:15
 * Email : alfa.king+git@gmail.com
 */
@Getter
@Setter
public class LoginModel implements Serializable {

  private static final long serialVersionUID = -339711558238425730L;

  @NotBlank(message = "{loginModel.name.null}")
  @Length(min = 2, max = 50, message = "{loginModel.name.length}")
  private String userId;

  @NotBlank(message = "{loginModel.password.null}")
  @Length(min = 6, max = 50, message = "{loginModel.password.length}")
  private String password;

}
