package com.megacity.server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.megacity.server.Model.Category;

@Repository
public interface CategoryRepository  extends MongoRepository<Category , String>{
    
}
