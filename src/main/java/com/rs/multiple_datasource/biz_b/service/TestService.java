package com.rs.multiple_datasource.biz_b.service;

import com.rs.multiple_datasource.biz_b.model.TestEntity;
import com.rs.multiple_datasource.biz_b.persistence.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "jpaTransactionManager")
public class TestService {
    @Autowired
    TestRepository testRepository;

    public TestEntity select(Integer id){
        return testRepository.findById(id).get();
    }
}
