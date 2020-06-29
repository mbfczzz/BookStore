package com.example.demo.Service;

import com.example.demo.Dao.BookDao;
import com.example.demo.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    public List<Book> getAllBook(){
        return bookDao.findAll();
    }
    public  List<Book> ListCategoryPage(int cid,int page,int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
        List<Book> books = bookDao.findAllByCid(cid,pageable);
        return books;

    }
    public List<Book> ListPage(int page,int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
         return  bookDao.findAll(pageable).getContent();
    }
    public List<Book> SearchPage(String name,int page,int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
        return bookDao.findAllByTitleLikeOrAuchorLikeOrPriceLike("%"+name+"%","%"+name+"%","%"+name+"%",pageable);
    }
 }
