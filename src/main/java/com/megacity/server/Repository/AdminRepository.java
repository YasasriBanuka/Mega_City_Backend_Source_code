package com.megacity.server.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.megacity.server.Model.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin , String>{

    Optional<Admin> findByUserName(String userName);
    boolean existsByUserName(String userName);
} 