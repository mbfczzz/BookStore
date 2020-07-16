package com.example.demo.Controller;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Layui;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping("/index")
    public  ModelAndView index(@RequestParam(required = true,defaultValue ="1",name = "pagen") String pagen){
        ModelAndView m = new ModelAndView();
        int page= Integer.parseInt(pagen)-1;
        int pagesize = 12;
        int npage = page+1;
        if(npage>5){
            m.addObject("n",npage);
        }
        List<Book> books = bookService.ListPage(page,pagesize);
        m.setViewName("index");
        m.addObject("books",books);
        return m;
    }

    @GetMapping("/book/search")
    public ModelAndView search(@RequestParam(required = true,defaultValue ="1",name = "pagen") String pagen,@RequestParam(name = "search") String srarch){
        ModelAndView m = new ModelAndView();
        int page= Integer.parseInt(pagen)-1;
        int pagesize = 12;
        int npage = page+1;
        if(npage>5){
            m.addObject("n",npage);
        }
        List<Book> books = bookService.SearchPage(srarch,page,pagesize);
        m.setViewName("index");
        m.addObject("books",books);
        return m;
    }

    @GetMapping(value = "/category/{cid}/book")
    public ModelAndView book(@PathVariable int cid, @RequestParam(required = true,defaultValue ="1",name = "pagen") String pagen){
            int page= Integer.parseInt(pagen)-1;
            int pagesize = 12;
            int npage = page+1;
            ModelAndView m = new ModelAndView();
            if(npage>5){
                m.addObject("n",npage);
            }
        if(cid == 0){
            List<Book> books = bookService.ListPage(page,pagesize);
            m.setViewName("index");
            m.addObject("books",books);
            return  m;
        }
       List<Book> books = bookService.ListCategoryPage(cid,page,pagesize);
       m.setViewName("index");
       m.addObject("books",books);
       return  m;
    }
    @GetMapping("/book/goods")
    public ModelAndView goods(@RequestParam(name = "goodsid") String goodsid){
        int id = Integer.parseInt(goodsid);
        Book book = bookService.getByid(id);
        ModelAndView m = new ModelAndView();
        m.addObject("book",book);
        m.setViewName("goods");
        return  m;
    }
    @GetMapping("/admin/book")
    @ResponseBody
    public Layui getAllBook(@RequestParam(name = "page") String page, @RequestParam(name = "limit") String limit){
        int page1= Integer.parseInt(page)-1;
        int pagesize = Integer.parseInt(limit);
        Layui layui = new Layui();
        layui.setCode(0);
        layui.setCount(1000);
        layui.setMsg("");
        layui.setData(bookService.getBookBypage(page1,pagesize));
        return  layui;
    }
}
