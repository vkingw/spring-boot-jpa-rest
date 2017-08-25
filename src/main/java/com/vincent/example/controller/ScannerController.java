package com.vincent.example.controller;

import com.vincent.example.authorization.annotation.CurrentUser;
import com.vincent.example.model.Book;
import com.vincent.example.model.User;
import com.vincent.example.repository.BooksRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created : vincent
 * Date : 2017/8/1 下午3:59
 * Email : wangxiao@wafersystems.com
 */
@RepositoryRestController
@RequestMapping("api/scanners")
public class ScannerController {

  private final BooksRepository booksRepository;

  @Autowired
  public ScannerController(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  @GetMapping
  @ApiOperation(value = "Get Company", notes = "Get Company")
  public @ResponseBody
  ResponseEntity<?> getCompany(@CurrentUser User user) {
    Assert.notNull(user, "User can not be empty");
    Iterable<Book> companies = booksRepository.findAll();
    List<Book> companyList = StreamSupport.stream(companies.spliterator(), false).collect(toList());

    Resources<Book> companyResource = new Resources<>(companyList);
    companyResource.add(linkTo(methodOn(ScannerController.class).getCompany(user)).withSelfRel());
    companyList.forEach(v -> {
      Link link = linkTo(ScannerController.class).slash(v.getId()).withSelfRel();
      companyResource.add(link);
    });
    return ResponseEntity.ok(companyResource);
  }
}
