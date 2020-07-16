package com.example.demo.Service;

import com.example.demo.Dao.ShopcarDao;
import com.example.demo.Entity.Book;
import com.example.demo.Entity.Shopcar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopcarService {
    @Autowired
    ShopcarDao shopcarDao;
    @Autowired
    BookService bookService;
    public void Save(Shopcar shopcar){
        shopcarDao.save(shopcar);
    }
    public List<Shopcar> getAll(int page,int limit){
        Pageable pageable = PageRequest.of(page,limit);
       List<Shopcar> shopcars =  shopcarDao.findAll(pageable).getContent();
        for (Shopcar s:shopcars
             ) {
                int id = s.getBid();
                Book book =  bookService.getByid(id);
                s.setAmount(book.getAmount());
                s.setBookname(book.getTitle());
        }
        return shopcars;
    }
}
