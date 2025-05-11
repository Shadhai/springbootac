package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsern(String usern);
    // You can add custom query methods here if needed
}