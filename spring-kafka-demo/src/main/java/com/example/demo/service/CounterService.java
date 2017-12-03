package com.example.demo.service;

import com.example.demo.dao.CounterRepository;
import com.example.demo.domain.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CounterService {
    private MongoOperations mongo;
    private CounterRepository counterRepository;

    public long getNextSequence(String collectionName) {
        if (counterRepository.findOneById(collectionName)==null){
            Counter counter=new Counter();
            counter.setId("hotels");
            counter.setSeq(0);
            counterRepository.save(counter);
        }
        Counter counter = mongo.findAndModify(
                query(where("_id").is(collectionName)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                Counter.class);
        return counter.getSeq();
    }

    @Autowired
    public CounterService(MongoOperations mongo, CounterRepository counterRepository) {
        this.mongo = mongo;
        this.counterRepository=counterRepository;
    }
}
