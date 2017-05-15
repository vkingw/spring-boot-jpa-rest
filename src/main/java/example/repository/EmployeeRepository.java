package example.repository;

import example.bean.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:31
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    List<Employee> findByLastName(@Param("name") String name);


}
