package com.megacity.server.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.megacity.server.Model.Category;
import com.megacity.server.Service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addcategory")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/updatecategory/{id}")
    public Category updateCategory(@PathVariable("id") String categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return "Cab with ID " + categoryId + " has been deleted.";
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/auth/getallcategories")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    
}
