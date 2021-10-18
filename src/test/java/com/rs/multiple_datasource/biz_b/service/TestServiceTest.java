package com.rs.multiple_datasource.biz_b.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    TestService testService;

    @Test
    public void test(){
        System.out.println(testService.select(1).getData());
    }

}