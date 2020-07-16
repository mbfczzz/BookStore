package com.example.demo.Dao;

import com.example.demo.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Integer> {
    List<Book> findAllByCid(int cid,Pageable pageable);
    List<Book> findAllByTitleLikeOrAuchorLikeOrPriceLike(String Title,String Auchor,String Price,Pageable pageable);
    Book findById(int id);
}
