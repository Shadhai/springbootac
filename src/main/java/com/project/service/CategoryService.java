package com.project.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Category;
import com.project.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with this name already exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    if (!category.getName().equals(updatedCategory.getName()) && categoryRepository.existsByName(updatedCategory.getName())) {
                        throw new IllegalArgumentException("Category with this name already exists.");
                    }
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}