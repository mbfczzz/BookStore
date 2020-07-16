package com.example.demo.shiro;

import com.example.demo.Entity.User;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.permissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    permissionService permissionService;
    @Autowired
    RoleService roleService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = principalCollection.getPrimaryPrincipal().toString();
        //查询数据库得到所有的权限列表
        List<String> urllist=new ArrayList<String>();
        List<String> roles =  new ArrayList<>();
        User user = userService.getByUername(username);
        urllist = permissionService.getUrl(user.getId());
        roles = roleService.getAllroleByName(user.getId());
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(urllist);
        authorizationInfo.addRoles(roles);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getByUsernameOrEmail(username);
        String password = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(salt),getName());
        return simpleAuthenticationInfo;
    }
}
