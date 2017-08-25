package com.vincent.example;

import com.vincent.example.model.Book;
import com.vincent.example.model.Company;
import com.vincent.example.model.Employee;
import com.vincent.example.model.User;
import com.vincent.example.repository.BooksRepository;
import com.vincent.example.repository.CompanyRepository;
import com.vincent.example.repository.EmployeeRepository;
import com.vincent.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:35
 * Email : wangxiao@wafersystems.com
 */
@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class Application {

  final
  CompanyRepository companyRepository;
  final
  EmployeeRepository employeeRepository;
  final
  BooksRepository booksRepository;
  final
  UserRepository userRepository;

  @Autowired
  public Application(CompanyRepository companyRepository, EmployeeRepository employeeRepository, BooksRepository booksRepository, UserRepository userRepository) {
    this.companyRepository = companyRepository;
    this.employeeRepository = employeeRepository;
    this.booksRepository = booksRepository;
    this.userRepository = userRepository;
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

  public @PostConstruct
  void init() {

    booksRepository.save(new Book("test"));
    booksRepository.save(new Book("test2"));
    Company apple = companyRepository.save(new Company("Apple"));
    Company google = companyRepository.save(new Company("Google"));

    Employee employee = new Employee();
    employee.setFirstName("Bilbo");
    employee.setLastName("Baggins");
    employee.setAge(42);
    employee.setCompany(google);
    employeeRepository.save(employee);

    employee = new Employee();
    employee.setFirstName("Bilbo2");
    employee.setLastName("Baggins2");
    employee.setAge(22);
    employee.setCompany(google);
    employeeRepository.save(employee);

    employee = new Employee();
    employee.setFirstName("Bilbo3");
    employee.setLastName("Baggins3");
    employee.setAge(32);
    employee.setCompany(apple);
    employeeRepository.save(employee);

    User user = new User();
    user.setName("admin");
    user.setPassword("password");
    user.setNickname("VK");
    userRepository.save(user);

    User user2 = new User();
    user2.setName("a1");
    user2.setPassword("password");
    user2.setNickname("VK2");
    userRepository.save(user2);
  }
}
