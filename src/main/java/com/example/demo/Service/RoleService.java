package com.example.demo.Service;

import com.example.demo.Dao.RoleDao;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.Userole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    RoleDao roleDao;
    @Autowired
    UseroleService useroleService;
    public List<String> getAllroleByName(int id){
        List<Integer> rid = useroleService.getRid(id).stream().map(Userole::getRid).collect(Collectors.toList());
        List<String>  roles = roleDao.findAllById(rid).stream().map(Role::getRolename).collect(Collectors.toList());
        return roles;
    }
    public  List<Role> getRoleById(List<Integer> rids){
        return roleDao.findAllById(rids);
    }
    public List<Role> getRole(){
        return roleDao.findAll();
    }
}
