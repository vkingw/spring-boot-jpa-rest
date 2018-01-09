package com.vincent.example.repository;

import com.vincent.example.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:47
 * Email : alfa.king+git@gmail.com
 */
@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
}
