package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users") // Changed table name to "users" for the new class
public class User {
    @Id
    private String usern;
    private String pwd;
    private String role;
    private String email;
    private String phone;

    // Default constructor (required by JPA)
    public User() {
    }

    // Getters and setters
    public String getUsern() {
        return usern;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User [usern=" + usern + ", pwd=" + pwd + ", role=" + role + ", email=" + email + ", phone=" + phone + "]";
    }
}