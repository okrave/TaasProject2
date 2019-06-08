package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.newEntities.AppUserRegistration;
import com.example.togroup5.demo.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.togroup5.demo.utils.EncryptedPasswordUtils.encryptePassword;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RestHomeController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/ping")
    public boolean ping(){
        return true;
    }
    @GetMapping(value = "/resthome")
    public String resthome(){
        // Il tipo di ritorno String viene interpretato come JSON
        return "[\"greetings\", 7, \"ciao\"]";
    }

    @GetMapping(value = "/User/createUser")
    public void createUsers(){
        //(String userName, String encrytedPassword, String userEmail, boolean enabled)
        AppUser user = new AppUser("dbuser1",encryptePassword("123"),"lol@lol.it",true);
        userService.save(user);
        user = new AppUser("dbadmin1",encryptePassword("123"),"lol@lolool.it",true);
        userService.save(user);
        user = new AppUser("ciao",encryptePassword("123"),"lol@lolool.it",true);
        userService.save(user);
        user = new AppUser("lonevetad",encryptePassword("lonevetad"),"lone@vetad.it",true);
        userService.save(user);
        user = new AppUser("Bender",encryptePassword("im_drunk"),"im_drunk@beer.it",true);
        userService.save(user);
    }

    @RequestMapping(
            value = "/User/register",
            method = POST
    )
    public boolean register(@RequestBody AppUserRegistration newUser){
        AppUser user;
        user = newUser.toAppUser();
        if(!userService.containsUser(user)) {
            userService.save(user);
            return true;
        }
        return false;
    }


    @GetMapping(value= "/User/listAllUsers")
    public List<AppUser> listAllUsers(Model model){
        List<AppUser> allUser = userService.findAll();
        return allUser;
    }



    /**Remove the user identified by its ID.
     * @return true if the user existed and then is successfully removed, false otherwise.*/
    @DeleteMapping(value = "/User/deleteByID/{userId}")
    public boolean deleteUserByID(@PathVariable Long userId){
        return userService.delete(userId);
    }

    /**Remove the user identified by its username.
     * @return true if the user existed and then is successfully removed, false otherwise.*/
    @DeleteMapping(value = "/User/deleteByUsername/{userName}")
    public boolean deleteUserByUsername(@PathVariable String userName){
        return userService.delete(userName);
    }

}
