package com.example.demo.Dao;

import com.example.demo.Entity.rolepermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface rolepermissionDao extends JpaRepository<rolepermission,Integer> {
    List<rolepermission> findAllByRidIn(List<Integer> rids);
}
