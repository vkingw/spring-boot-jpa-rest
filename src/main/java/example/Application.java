package example;

import example.bean.Company;
import example.bean.Employee;
import example.repository.CompanyRepository;
import example.repository.EmployeeRepository;
import example.security.ExampleAuthenticationFilter;
import example.security.ExampleAuthenticationUserDetailsService;
import example.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.annotation.PostConstruct;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:35
 * Email : wangxiao@wafersystems.com
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    public @PostConstruct
    void init() {

        Company apple = companyRepository.save(new Company("Apple"));
        Company google = companyRepository.save(new Company("Google"));

        employeeRepository.save(new Employee("Bilbo", "Baggins", 12, apple));
        employeeRepository.save(new Employee("Frodo", "Baggins", 22, apple));
        employeeRepository.save(new Employee("Gandalf", "the Wizard", 32, google));
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(AuthenticationManagerBuilder builder) throws Exception {
            builder.authenticationProvider(preAuthenticationProvider());
        }

        private AuthenticationProvider preAuthenticationProvider() {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new ExampleAuthenticationUserDetailsService());
            return provider;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .headers()
                    .frameOptions()
                    .disable();
            http
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint());
            http.addFilter(headerAuthenticationFilter());
        }

        @Bean
        public ExampleAuthenticationFilter headerAuthenticationFilter() throws Exception {
            return new ExampleAuthenticationFilter(authenticationManager());
        }
    }

}
