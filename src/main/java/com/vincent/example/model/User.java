package com.vincent.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created : vincent
 * Date : 2017/8/2 下午10:07
 * Email : alfa.king+git@gmail.com
 */
@Entity
@Getter
@Setter
public class User implements Serializable {

  private static final long serialVersionUID = -6580471173624829108L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  private String nickname;

  private String userId;

  private String password;

  private String roleNames;

  private Date expirationDate;

  private Gender gender;

  private int age;

  public static enum Gender {
    MALE, FEMALE;
  }
}
