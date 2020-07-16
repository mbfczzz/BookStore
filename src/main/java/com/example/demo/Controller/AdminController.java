package com.example.demo.Controller;

import com.example.demo.Entity.BindResult;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UseroleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    UseroleService useroleService;
    @GetMapping("/admin/index")
    public ModelAndView toAdminindex(){
        ModelAndView m = new ModelAndView();
        Subject subject= SecurityUtils.getSubject();
        if(subject.isAuthenticated() == false){
            m.setViewName("login");
            return m;
        }
        String username = subject.getPrincipal().toString();
        User user = userService.getByUername(username);
        m.addObject("user",user);
        m.setViewName("AdminPerson");
        return m;
    }
    @GetMapping("/admin/userinform")
    public  ModelAndView toperson(){
        ModelAndView m = new ModelAndView();
        List<Role> role = roleService.getRole();
        m.addObject("role",role);
        m.setViewName("AdminUser");
        return m;
    }

    @PostMapping("/admin/editorUser")
    @ResponseBody
    public BindResult editor(@RequestBody User user){
        int id = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        List<Role> role = user.getRoleList();
        User user1 = userService.getUserById(id);
        user1.setUsername(username);
        user1.setName(name);
        user1.setPhone(phone);
        user1.setEmail(email);
        useroleService.savechange(user.getId(),role);
        userService.update(user1);
        return  new BindResult("200");
    }
    @GetMapping("/admin/bookpage")
    public  ModelAndView tobookpage(){
        ModelAndView m = new ModelAndView();
        m.setViewName("Adminbook");
        return m;
    }
    @GetMapping("/admin/resetPassword")
    @ResponseBody
    public BindResult resetPassword(){
        Subject subject= SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        User user = userService.getByUername(username);
        if(user == null){
            return new BindResult("用户未登录");
        }
        String password= "123456";
        String salt =  new SecureRandomNumberGenerator().nextBytes().toString();
        int times=2;
        String encodePassword= new SimpleHash("md5",password,salt,times).toString();
        user.setSalt(salt);
        user.setPassword(encodePassword);
        userService.update(user);
        return new BindResult("密码重置为"+123456);
    }
    @PostMapping("/admin/updateUser")
    @ResponseBody
    public BindResult updateUser(@RequestBody User user){
        String name = user.getName();
        Subject subject= SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        User user1 =userService.getByUername(username);
        if(user1 == null){
            return new BindResult("用户未登录或未存在!");
        }
        String phone = user.getPhone();
        String email = user.getEmail();
        Boolean enable = user.getEnable();
        user1.setUsername(username);
        user1.setPhone(phone);
        user1.setEmail(email);
        user1.setEnable(enable);
        userService.update(user1);
        return new BindResult("更新成功!");
    }
    @PostMapping("/admin/changeStatus")
    @ResponseBody
    public BindResult changeStatus(@RequestBody User user){
        Boolean enable = user.getEnable();
        int id = user.getId();
        User user1 = userService.getUserById(id);
        user1.setEnable(enable);
        userService.update(user1);
        return new BindResult("200");

    }
}
