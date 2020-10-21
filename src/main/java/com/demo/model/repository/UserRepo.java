package com.demo.model.repository;

import com.demo.model.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    
    public User findByEmail(String email);
}
