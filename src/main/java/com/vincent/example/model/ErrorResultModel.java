package com.vincent.example.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * 自定义错误返回结果
 * Created : vincent
 * Date : 2017/5/11 上午10:24
 * Email : alfa.king+git@gmail.com
 */
@Getter
@Setter
public class ErrorResultModel {

  /**
   * 返回码
   */
  private int status;

  /**
   * 返回结果描述
   */
  private String error;

  /**
   * 返回内容
   */
  private String message;

  private long timestamp;

  private String path;

  public static ResponseEntity<ErrorResultModel> error(HttpStatus httpStatus, String error) {
    ErrorResultModel errorResultModel = new ErrorResultModel();
    errorResultModel.setTimestamp(new Date().getTime());
    errorResultModel.setStatus(httpStatus.value());
    errorResultModel.setError(httpStatus.getReasonPhrase());
    errorResultModel.setMessage(error);
    return new ResponseEntity<>(errorResultModel, httpStatus);
  }

}
