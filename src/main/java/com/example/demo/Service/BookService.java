package com.example.demo.Service;

import com.example.demo.Dao.BookDao;
import com.example.demo.Entity.Book;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Book getByid(int id){
        return  bookDao.findById(id);
    }
    public List<Book> ListPage(int page,int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
         return  bookDao.findAll(pageable).getContent();
    }

    public List<Book> SearchPage(String name,int page,int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
        return bookDao.findAllByTitleLikeOrAuchorLikeOrPriceLike("%"+name+"%","%"+name+"%","%"+name+"%",pageable);
    }

    public List<Book> getBookBypage(int page,int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return bookDao.findAll(pageable).getContent();
    }
 }
