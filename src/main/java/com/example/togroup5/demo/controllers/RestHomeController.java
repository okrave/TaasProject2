package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.newEntities.AppUserRegistration;
import com.example.togroup5.demo.entities.payloadsResults.UserLoginPayload;
import com.example.togroup5.demo.entities.payloadsResults.UserIDName;
import com.example.togroup5.demo.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/User/register", method = POST)
    public boolean register(@RequestBody AppUserRegistration newUser){
        AppUser user;
        user = newUser.toAppUser();
        if(!userService.containsUser(user)) {
            userService.save(user);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/User/customLogin", method = POST)
    public AppUser login(@RequestBody AppUserRegistration newUser, HttpSession session){
        //Ricevo la password già criptata
        System.out.println(newUser);

        AppUser x = userService.findAppUserByUserName(newUser.getUserName());
        session.setAttribute("username",x.getUserName());
        session.setAttribute("userId",x.getUserId());
        System.out.println("Trovato L'utente: " + x) ;
        return x;
    }



    @GetMapping(value = "/User/login")
    public UserIDName logIn(@RequestParam String email, @RequestParam String password){
        AppUser user;
        UserLoginPayload userPayload;
        userPayload = new UserLoginPayload(email, password);
        user = userService.findByEmailPassword(userPayload);
        if(user == null )
            return null;
        return new UserIDName(user.getUserId(), user.getUserName());
    }


    @GetMapping(value= "/User/listAllUsers")
    public List<AppUser> listAllUsers(Model model){
        List<AppUser> allUser = userService.findAll();
        return allUser;
    }


    @GetMapping(value = "/User/info/{userId}")
    public AppUser infoUser(@PathVariable String userId ){
        System.out.println("groupId:" + userId);
        AppUser newUser = userService.findUserById(new Long(userId));

        return newUser;
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


    // populate db with default staffs

    @GetMapping(value = "/User/createUser")
    public void createUsers(){
        //(String userName, String encrytedPassword, String userEmail, boolean enabled)
        AppUser user = new AppUser("dbuser1",encryptePassword("123"),"lol@lol.it",true);
        userService.save(user);
        user = new AppUser("dbadmin1",encryptePassword("123"),"lol@lolool.it",true);
        userService.save(user);
        user = new AppUser("ciao",encryptePassword("123"),"lol@lolool.it",true);
        userService.save(user);
        user = new AppUser("Luca",encryptePassword("Luca"),"luca@luca.com",true);
        userService.save(user);
        user = new AppUser("Davide",encryptePassword("Davide"),"davide@qualcuno.en",true);
        userService.save(user);
        user = new AppUser("lonevetad",encryptePassword("lonevetad"),"lone@vetad.it",true);
        userService.save(user);
        user = new AppUser("Bender",encryptePassword("im_drunk"),"im_drunk@beer.it",true);
        userService.save(user);
    }

    @PostMapping(value = "/User/createAll")
    public void createAllHomeData(){
        createUsers();
        // create role and RoleUser
    }

    @RequestMapping("/login-user")
    public AppUser loginUser(@ModelAttribute AppUser user, HttpServletRequest request){
        return userService.findByUsernameAndPassword(user.getUserName(),user.getEncrytedPassword());

    }

}
