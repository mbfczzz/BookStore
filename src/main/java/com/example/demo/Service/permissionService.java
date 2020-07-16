package com.example.demo.Service;


import com.example.demo.Dao.permissionDao;
import com.example.demo.Dao.rolepermissionDao;
import com.example.demo.Entity.Userole;
import com.example.demo.Entity.permission;
import com.example.demo.Entity.rolepermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class permissionService {
    @Autowired
    RoleService roleService;
    @Autowired
    UseroleService useroleService;
    @Autowired
    rolepermissionDao rolepermissionDao;
    @Autowired
    permissionDao permissionDao;
    public List<String> getUrl(int uid){
        List<Integer> rid =  useroleService.getRid(uid).stream().map(Userole::getRid).collect(Collectors.toList());
        List<Integer> pid =  rolepermissionDao.findAllByRidIn(rid).stream().map(rolepermission::getPid).collect(Collectors.toList());
        List<permission> permissions  = permissionDao.findAllById(pid);
        List<String> url = new ArrayList<>();
        for(permission p:permissions){
            url.add(p.getUrl());
        }
        return  url;
    }
}
