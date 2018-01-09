package com.vincent.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created : vincent
 * Date : 2017/10/9 下午8:27
 * Email : alfa.king+git@gmail.com
 */
@Getter
public class ControllerException extends Exception {

  private static final long serialVersionUID = -446375198350395568L;
  private HttpStatus httpStatus;

  public ControllerException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
