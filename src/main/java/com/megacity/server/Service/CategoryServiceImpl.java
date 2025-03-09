package com.megacity.server.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacity.server.Model.Category;
import com.megacity.server.Repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
      
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Cab not found with ID: " + categoryId));
        
    }

    @Override
    public Category updateCategory(String categoryId, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            category.setCategoryId(categoryId);
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Cab not found with ID: " + categoryId);
    }
    
}
