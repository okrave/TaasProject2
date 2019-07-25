package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.payloadsResults.UserLoginPayload;
import com.example.togroup5.demo.repositories.AppRoleRepository;
import com.example.togroup5.demo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//Utilizza i metodi che sono implementati nella appUserRepository
@Service
public class UserService {


    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    //basic methods

    public void save(AppUser user){
        appUserRepository.save(user);
    }

    public boolean delete(Long userId){
        if(containsUser(userId)) {
            appUserRepository.delete(userId);
            return true;
        }
        return false;
    }

    public boolean delete(String username){
        return appUserRepository.delete(username);
    }

    // query methods

    public boolean containsUser(AppUser user){
        return appUserRepository.findAppUserByUserName(user.getUserName()) != null;
    }

    public boolean containsUser(Long userId){
        return appUserRepository.findAppUserByID(userId) != null;
    }

    public AppUser findByUsername(String userName){
        return appUserRepository.findUserAccount(userName);
    }

    public AppUser findAppUserByUserName(String userName){
        return appUserRepository.findAppUserByUserName(userName);
    }
    public List<AppUser> findAll(){
        return appUserRepository.findAll();
    }

    public AppUser findUserById(Long userId){ return appUserRepository.findAppUserByID(userId);}

    public AppUser findByUsernameAndPassword(String userName, String password){
        return appUserRepository.findByUserNameAndPassword(userName,password);
    }
    public AppUser findByEmailPassword(UserLoginPayload userEmailPassword){
        return appUserRepository.findByEmailPassword(userEmailPassword);
    }
    public AppUser findByEmailOrUsernameAndPassword(UserLoginPayload userEmailPassword){
        return appUserRepository.findByEmailOrUsernameAndPassword(userEmailPassword);
    }
}
