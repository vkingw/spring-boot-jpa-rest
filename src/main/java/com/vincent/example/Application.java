package com.vincent.example;

import com.vincent.example.model.Company;
import com.vincent.example.model.Employee;
import com.vincent.example.model.User;
import com.vincent.example.repository.CompanyRepository;
import com.vincent.example.repository.EmployeeRepository;
import com.vincent.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:35
 * Email : alfa.king+git@gmail.com
 */
@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class Application {

  private final CompanyRepository companyRepository;
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;

  @Autowired
  public Application(CompanyRepository companyRepository, EmployeeRepository employeeRepository, UserRepository
    userRepository) {
    this.companyRepository = companyRepository;
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public @PostConstruct
  void init() {

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
    String stronger_salt = BCrypt.gensalt(8);
    user.setPassword(BCrypt.hashpw("password", stronger_salt));
    user.setNickname("VK");
    user.setUserId("admin");
    userRepository.save(user);

    User user2 = new User();
    user2.setName("a1");
    user2.setPassword("password");
    user2.setNickname("VK2");
    userRepository.save(user2);
  }
}
