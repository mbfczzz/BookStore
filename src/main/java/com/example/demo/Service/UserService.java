package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Userole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Value("${spring.mail.username}")
    private  String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UseroleService useroleService;
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
    public User getUserById(int id){
        return userDao.findAllById(id);
    }
    public List<User> getUserBypage(int page, int limit){
        Pageable pageable = PageRequest.of(page,limit);
        List<User> users = userDao.findAll(pageable).getContent();
        for(User user:users){
            int id = user.getId();
            List<Integer> rids = useroleService.getRid(id).stream().map(Userole::getRid).collect(Collectors.toList());
            List<Role> roles = roleService.getRoleById(rids);
            user.setRoleList(roles);
        }
        return users;
    }

}
