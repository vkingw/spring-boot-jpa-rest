package com.vincent.example.model;

import lombok.*;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created : vincent
 * Date : 2017/8/1 下午4:05
 * Email : wangxiao@wafersystems.com
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = -5165517824624649940L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Description("Name need string")
    @NonNull
    private String name;
}
