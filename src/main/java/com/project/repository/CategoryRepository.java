package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}