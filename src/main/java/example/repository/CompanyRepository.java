package example.repository;

import example.bean.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:47
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {


}
