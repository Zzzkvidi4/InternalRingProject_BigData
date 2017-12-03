package com.example.demo.dao;

import com.example.demo.domain.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter,String> {
    Counter findOneById(String id);
}
