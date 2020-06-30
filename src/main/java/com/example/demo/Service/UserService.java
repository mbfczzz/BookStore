package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

@Service
public class UserService {
    @Value("${spring.mail.username}")
    private  String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserDao userDao;
    public User get(String username, String password){
        User user = userDao.findByUsernameAndPassword(username,password);
        return  user;
    }
    public  User getByeamilAndPassword(String email,String password){
        User user = userDao.findByEmailAndPassword(email,password);
        return user;
    }
    public User getByemail(String email){
        User user = userDao.findByEmail(email);
        return user;
    }
    public  User getByUername(String username){
        User user = userDao.findAllByUsername(username);
        return  user;
    }
    public User getByUsernameOrEmail(String username){
        return  userDao.findByEmailOrUsername(username,username);
    }
    public void addUser(User user){
        update(user);
    }
    public  void Save(User user){
        userDao.save(user);
    }
    public boolean nameisExist(String  username){
        return userDao.existsByUsername(username);
    }
    public boolean emailisExist(String email){
        return userDao.existsByEmail(email);
    }
    public  void update(User user){
        userDao.save(user);
    }

    public void sendSimpleMail(String to,String title,String content){
        System.out.println(from);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

}
