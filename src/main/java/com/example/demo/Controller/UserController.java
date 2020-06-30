package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.utils.RandomValidateCodeUtil;
import com.example.demo.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/user/login")
    public ModelAndView login(@RequestParam("username") String username,@RequestParam("password") String password){
        username = HtmlUtils.htmlEscape(username);
        ModelAndView m = new ModelAndView();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password,true);
        try {
            subject.login(usernamePasswordToken);
            m.setViewName("redirect:/index");
            return m;
        } catch (AuthenticationException e) {
            System.out.println(e);
            m.setViewName("login");
            m.addObject("mes","用户不存在或者密码错误");
            return m;
        }
    }

    @GetMapping("/")
    public String login1(){
        return "login";
    }
    @GetMapping("/login")
    public String login2(){
        return "login";
    }
    @GetMapping("/register")
    public  String register(){
        return "Sign_up";
    }

    @GetMapping("/fgpassword")
    public String fgpassword(){
        return "forgot_password";
    }

    @GetMapping("/getcheckcode")
    @ResponseBody
    public String getcheckcode(String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        System.out.println(checkCode);
        try {
            userService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
        System.out.println(checkCode);
        return checkCode;
    }

    @PostMapping("/user/changepassword")
    public ModelAndView changepassword(@RequestParam("email") String email,@RequestParam("password") String password){
            email = HtmlUtils.htmlEscape(email);
            password = HtmlUtils.htmlEscape(password);
            User user = userService.getByemail(email);
            ModelAndView m= new ModelAndView();
            if(user==null){
                m.setViewName("forgot_password");
                m.addObject("mes","用户不存在");
                return m;
            }
            String salt =  new SecureRandomNumberGenerator().nextBytes().toString();
            int times=2;
            String encodePassword= new SimpleHash("md5",password,salt,times).toString();
            user.setSalt(salt);
            user.setPassword(encodePassword);
            userService.update(user);
            m.setViewName("redirect:/login");
            return m;
    }

    @PostMapping("/user/resgister")
    public  ModelAndView resgister(@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("phone") String phone){
        username = HtmlUtils.htmlEscape(username);
        email = HtmlUtils.htmlEscape(email);
        password = HtmlUtils.htmlEscape(password);
        phone =HtmlUtils.htmlEscape(phone);
        boolean nameexist = userService.nameisExist(username);
        boolean emailexist = userService.emailisExist(email);
        ModelAndView m = new ModelAndView();
        if(nameexist || emailexist){
                m.setViewName("Sign_up");
                m.addObject("mes","此用户已存在");
                return  m;
        }
        User user1 = new User();
        String salt =  new SecureRandomNumberGenerator().nextBytes().toString();
        int times=2;
        String encodePassword= new SimpleHash("md5",password,salt,times).toString();
        user1.setSalt(salt);
        user1.setUsername(username);
        user1.setPassword(encodePassword);
        user1.setName("普通用户");
        user1.setEmail(email);
        user1.setPhone(phone);
        user1.setEnable(true);
        userService.addUser(user1);
        m.addObject("username",user1.getUsername());
        m.addObject("password",password);
        m.addObject("issubmit",true);
        m.setViewName("login");
        return m;
    }

    @PostMapping("api/resgister/upload")
    @ResponseBody
    public String upload(MultipartFile file){
            String folder = "D:/workspace/img";
            File imageFolder = new File(folder);
            File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                    .substring(file.getOriginalFilename().length() - 4));
            System.out.println(f.getParent());
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            try {
                file.transferTo(f);
                String imgURL = "http://localhost:8080/file/" + f.getName();
                return imgURL;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
        }
    }

    @GetMapping("getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @PostMapping("/checkVerify")
    public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = verifyInput;
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equals(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
