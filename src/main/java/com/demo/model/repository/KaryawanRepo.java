package com.demo.model.repository;

import com.demo.model.entity.Karyawan;

import org.springframework.data.repository.CrudRepository;

public interface KaryawanRepo extends CrudRepository<Karyawan, Integer> {
    
}
