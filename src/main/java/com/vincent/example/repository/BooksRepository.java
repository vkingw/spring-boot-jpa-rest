package com.vincent.example.repository;

import com.vincent.example.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created : vincent
 * Date : 2017/8/1 下午5:30
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BooksRepository extends PagingAndSortingRepository<Book, Long> {
}
