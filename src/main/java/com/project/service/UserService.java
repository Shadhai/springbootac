package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(User user) {
        if (userRepository.existsById(user.getUsern())) {
            return "User with username " + user.getUsern() + " already exists.";
        }
        userRepository.save(user);
        return "User created successfully.";
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findById(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String updateUser(User user) {
        if (userRepository.existsById(user.getUsern())) {
            userRepository.save(user);
            return "User updated successfully.";
        } else {
            return "User with username " + user.getUsern() + " not found.";
        }
    }

    public String deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            return "User deleted successfully.";
        } else {
            return "User with username " + username + " not found.";
        }
    }

    public User findByUsern(String usern) {
        return userRepository.findByUsern(usern);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public String updateUserRole(String usern, String newRole) {
        Optional<User> userOptional = userRepository.findById(usern);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(newRole);
            userRepository.save(user);
            return "Role for user " + usern + " updated successfully.";
        } else {
            return "User with username " + usern + " not found.";
        }
    }

    public String CheckUser(User user) {
        User userRet = userRepository.findByUsern(user.getUsern());
        if (userRet != null) {
            if (userRet.getPwd().equals(user.getPwd())) { // WARNING: Plain-text password check
                return userRet.getRole();
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }
}