package com.vincent.example.model;

import lombok.*;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:24
 * Email : wangxiao@wafersystems.com
 */
@Entity
@Getter
@Setter
@ToString
public class Employee implements Serializable {

  private static final long serialVersionUID = 6753217490577768184L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Description("First Name max 12 characters.")
  private String firstName;

  @Description("Last Name mx 12 characters.")
  private String lastName;

  @Description("Age max 2 digits.")
  private int age;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "companyId")
  /**
   * 在返回的json中包含company，默认是返回company的 ref
   */
  @RestResource(exported = false)
  private Company company;

  private Date joinDate;

}
