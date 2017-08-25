package com.vincent.example.repository;

import com.vincent.example.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:31
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
  EmployeeRepositoryCustom {

  /**
   * @param name search by name
   * @return employees
   */
  @RestResource(path = "name", rel = "name")
  List<Employee> findByLastName(@Param("name") String name);

  /**
   * 隐藏删除方法，借此可以隐藏自动生成的一些方法
   *
   * @param employee del employee
   */
  @RestResource(exported = false)
  void delete(Employee employee);

}
