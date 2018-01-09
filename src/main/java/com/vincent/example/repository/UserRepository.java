package com.vincent.example.repository;


import com.vincent.example.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User类的CRUD操作
 *
 * @see com.vincent.example.model.User
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : alfa.king+git@gmail.com
 */
public interface UserRepository extends CrudRepository<User, Long> {

  User findUserByUserId(String userId);
}
