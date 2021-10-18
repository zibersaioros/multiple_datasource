package com.rs.multiple_datasource.biz_b.persistence;

import com.rs.multiple_datasource.biz_b.model.TestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TestRepositoryTest {

    @Autowired
    TestRepository testRepository;

    @Test
    public void test(){
        TestEntity byId = testRepository.findById(1).get();
        System.out.println(byId.getData());
    }
}