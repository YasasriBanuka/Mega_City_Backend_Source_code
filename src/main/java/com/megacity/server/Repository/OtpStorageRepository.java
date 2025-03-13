package com.megacity.server.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.megacity.server.Model.OtpStorage;

@Repository
public interface OtpStorageRepository extends MongoRepository<OtpStorage, String>{
    Optional<OtpStorage> findByEmail(String email);
    void deleteByEmail(String email);

}
