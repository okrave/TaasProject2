package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.repositories.AppRoleRepository;
import com.example.togroup5.demo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//Utilizza i metodi che sono implementati nella appUserRepository
@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    public void save(AppUser user){
        //Si presume che la password e lo username siano gia stati riempiti
        user.setEnabled(true);
        //Prendo la password non criptata e la cripto
        //Bisogna settare i ruoli.
        user.setEncrytedPassword(user.getEncrytedPassword());
        appUserRepository.save(user);
    }

    /*public AppUser findByUsername(String userName){
        return appUserRepository.findByUsername(userName);
    }*/
}
