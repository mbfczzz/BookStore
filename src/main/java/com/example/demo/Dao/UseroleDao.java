package com.example.demo.Dao;

import com.example.demo.Entity.Userole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UseroleDao extends JpaRepository<Userole,Integer> {
    List<Userole> findAllByUid(int uid);
    void deleteByUid(int uid);
}
