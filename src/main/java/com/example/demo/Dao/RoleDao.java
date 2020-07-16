package com.example.demo.Dao;

import com.example.demo.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDao extends JpaRepository<Role,Integer> {
    List<Role> findAllByIdIn(List<Integer> rid);
}
