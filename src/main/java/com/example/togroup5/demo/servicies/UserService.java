package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.repositories.AppRoleRepository;
import com.example.togroup5.demo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;


//Utilizza i metodi che sono implementati nella appUserRepository
@Service
public class UserService {

    private User principal;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    public void save(AppUser user){
        appUserRepository.save(user);
    }

    public AppUser findByUsername(String userName){
        return appUserRepository.findUserAccount(userName);
    }

    public List<AppUser> findAll(){
        return appUserRepository.findAll();
    }

    public void setPrincipalUser(User principal){
        if(this.principal == null)
            this.principal = new User(principal.getUsername(), principal.getPassword(),principal.getAuthorities());
    }

    public User getPrincipalUser(){
        if(this.principal != null)
            return this.principal;
        return null;
    }
}
