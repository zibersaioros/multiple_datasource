package com.rs.multiple_datasource.biz_b.persistence;

import com.rs.multiple_datasource.biz_b.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer> {

}
