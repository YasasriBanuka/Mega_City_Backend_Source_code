package com.megacity.server.Service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.megacity.server.Model.Category;

@Service
public interface CategoryService {

    Category addCategory(Category category);
    Category updateCategory(String categoryId, Category category);
    void deleteCategory(String categoryId);
    Category getCategoryById(String categoryId);
    List<Category> getAllCategory();
    
}
