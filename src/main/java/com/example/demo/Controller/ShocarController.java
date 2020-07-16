package com.example.demo.Controller;

import com.example.demo.Entity.BindResult;
import com.example.demo.Entity.Book;
import com.example.demo.Entity.Layui;
import com.example.demo.Entity.Shopcar;
import com.example.demo.Service.BookService;
import com.example.demo.Service.ShopcarService;
import com.example.demo.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class ShocarController {
    @Autowired
    UserService userService;
    @Autowired
    ShopcarService shopcarService;
    @Autowired
    BookService bookService;
    @PostMapping("/shopcar/goods")
    @ResponseBody
    public BindResult addShopcar(@RequestBody Shopcar shopcar){
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated() == false){
                return  new BindResult("未登录!");
            }
            int  bid = shopcar.getBid();
            String date = shopcar.getSdate();
            System.out.println(date);
            Boolean ISBUY = false;
            float price = shopcar.getPrice();
            int num = shopcar.getNumber();
            Book book = bookService.getByid(bid);
            int amount = book.getAmount();
            if(num > amount){
                return new BindResult("此书库存不足"+num+"本");
            }
            float cprice = price*num;
            String username =  subject.getPrincipal().toString();
            int uid = userService.getByUername(username).getId();
            shopcar.setCprice(cprice);
            shopcar.setUid(uid);
            shopcar.setSdate(date);
            shopcar.setISBUY(ISBUY);
            shopcarService.Save(shopcar);
            return  new BindResult("200");
    }
    @GetMapping("/shopcar")
    public ModelAndView toShopcar(){
        ModelAndView m = new ModelAndView();
        m.setViewName("shopcar");
        return m;
    }
    @GetMapping("/getshopcar")
    @ResponseBody
    public Layui getshopcar(@RequestParam(name = "page") String page, @RequestParam(name = "limit") String limit){
        int page1 = Integer.parseInt(page)-1;
        int limit1 = Integer.parseInt(limit);
        Layui layui = new Layui();
        layui.setCode(0);
        layui.setCount(1000);
        layui.setMsg("");
        layui.setData(shopcarService.getAll(page1,limit1));
        return layui;
    }
}
