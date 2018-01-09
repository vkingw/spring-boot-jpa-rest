package com.vincent.example.handler;

import com.vincent.example.exception.ControllerException;
import com.vincent.example.model.ErrorResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created : vincent
 * Date : 2017/10/9 下午8:14
 * Email : wangxiao@wafersystems.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(ControllerException.class)
  public ResponseEntity handleControllerException(ControllerException e) {
    return ErrorResultModel.error(e.getHttpStatus(), e.getMessage());
  }

}
