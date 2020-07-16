package com.example.demo.Dao;

import com.example.demo.Entity.permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface permissionDao extends JpaRepository<permission,Integer> {
    List<permission> findAllByIdIn(List<Integer> pid);
}

