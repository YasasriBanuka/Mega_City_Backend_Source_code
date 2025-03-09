package com.megacity.server.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.megacity.server.Model.Cabs;

@Repository
public interface CabsRepository extends MongoRepository< Cabs , String>{

    List<Cabs>findByCategoryId(String categoryId);
    Optional<Cabs> findByUserName(String userName);
    
}
