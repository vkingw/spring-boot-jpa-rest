package com.vincent.example.repository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Created : vincent
 * Date : 2017/8/1 下午9:35
 * Email : wangxiao@wafersystems.com
 */
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    EntityManager entityManager;

    @Override
    public void delEmployee(Long id) {
        logger.debug("delete id is", id);
    }
}
