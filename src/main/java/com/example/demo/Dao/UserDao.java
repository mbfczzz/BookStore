package com.example.demo.Dao;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLTransactionRollbackException;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsernameAndPassword(String Username,String Password);
    User findByEmailAndPassword(String Email,String Password);
    User findByEmail(String Email);
    User findAllByUsername(String username);
    User findByEmailOrUsername(String Email,String username);
    Boolean  existsByUsername(String username);
}
