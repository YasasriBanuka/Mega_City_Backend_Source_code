package com.megacity.server.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.megacity.server.Model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer , String>{
// 
    Optional<Customer> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
