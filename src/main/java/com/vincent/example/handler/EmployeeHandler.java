package com.vincent.example.handler;

import com.vincent.example.model.Employee;
import com.vincent.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.Date;

/**
 * 此类演示了在创建 or 保存 Employee 时添加修改其属性
 * Created : vincent
 * Date : 2017/8/1 上午10:31
 * Email : wangxiao@wafersystems.com
 */
@RepositoryEventHandler(Employee.class)
public class EmployeeHandler {

  @Autowired
  CompanyRepository companyRepository;

  @HandleBeforeSave
  @HandleBeforeCreate
  public void handleEmployeeSave(Employee employee) {
    long id = 1L;
    employee.setJoinDate(new Date());
    employee.setCompany(companyRepository.findOne(id));
  }

}
