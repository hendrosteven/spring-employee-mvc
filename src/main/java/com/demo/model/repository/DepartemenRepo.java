package com.demo.model.repository;

import com.demo.model.entity.Departemen;

import org.springframework.data.repository.CrudRepository;

public interface DepartemenRepo extends CrudRepository<Departemen, Integer> {
    
}
