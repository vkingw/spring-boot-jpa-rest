package com.vincent.example.repository;


import com.vincent.example.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User类的CRUD操作
 *
 * @see com.vincent.example.model.User
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
public abstract class UserRepository implements CrudRepository<User, Long> {

  public abstract User findUserByName(String name);
}
