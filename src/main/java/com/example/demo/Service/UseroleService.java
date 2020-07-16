package com.example.demo.Service;

import com.example.demo.Dao.UseroleDao;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.Userole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UseroleService {
    @Autowired
    UseroleDao useroleDao;
    public List<Userole> getRid(int uid){
        return useroleDao.findAllByUid(uid);
    }
    @Transactional
    public  void savechange(int uid, List<Role> roleList){
        useroleDao.deleteByUid(uid);
        List<Userole> roles = new ArrayList<>();
        System.out.println(roleList);
        roleList.forEach(role -> {
            Userole u =new Userole();
            u.setUid(uid);
            u.setRid(role.getId());
            roles.add(u);
        });
        useroleDao.saveAll(roles);
    }
}
