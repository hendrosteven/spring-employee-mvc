package com.demo.model.repository;

import java.util.List;

import com.demo.model.entity.Karyawan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface KaryawanRepo extends CrudRepository<Karyawan, Integer> {
    
    @Query("SELECT k FROM Karyawan k WHERE k.departemen.id = :departemenId")
    public List<Karyawan> findAllByDepartemenId(@Param("departemenId") int departemenId);

}
