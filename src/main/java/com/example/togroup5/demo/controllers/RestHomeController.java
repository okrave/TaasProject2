package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.payloadsResults.AppUserRegistration;
import com.example.togroup5.demo.entities.payloadsResults.UserLoginPayload;
import com.example.togroup5.demo.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import static com.example.togroup5.demo.utils.EncryptedPasswordUtils.encryptePassword;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RestHomeController {


    @Resource(name = "authenticationManager")
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/ping")
    public boolean ping() {
        return true;
    }

    @GetMapping(value = "/resthome")
    public String resthome() {
        // Il tipo di ritorno String viene interpretato come JSON
        return "[\"greetings\", 7, \"ciao\"]";
    }

    @RequestMapping(value = "/User/register", method = POST)
    public boolean register(@RequestBody AppUserRegistration newUser) {
        AppUser user;
        user = newUser.toAppUser();
        if (!userService.containsUser(user)) {
            userService.save(user);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/User/customLogin", method = POST)
    public AppUser login(@RequestBody AppUserRegistration newUser, final HttpServletRequest request) {
        //Ricevo la password già criptata
        System.out.println(newUser);
/*
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(newUser.getUserName(), newUser.getPassword());
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);*/

        AppUser x = userService.findAppUserByUserName(newUser.getUserName());
        if (x != null && newUser.getPassword() == x.getEncrytedPassword()) {
            return x;

        }
        return null;
        //System.out.println("Trovato L'utente: " + x) ;
        //return x;
    }


    @RequestMapping("/User/facebookUser")
    public Principal user(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/User/login", method = PATCH)
    public AppUser logIn(//@RequestParam String email, @RequestParam String password OLD VERSION
                         @RequestBody UserLoginPayload logInInfo
    ) {

        AppUser user;
        UserLoginPayload userPayload;
        //userPayload = new UserLoginPayload(email, password);

        System.out.println(logInInfo);
        user = userService.findByEmailOrUsernameAndPassword(logInInfo);
        System.out.println("Dentro login controller: " + user);
        if (user == null)
            return null;
        return user; // new UserIDName(user.getUserId(), user.getUserName());
    }


    @GetMapping(value = "/User/listAllUsers")
    public List<AppUser> listAllUsers(Model model) {
        List<AppUser> allUser = userService.findAll();
        return allUser;
    }


    @GetMapping(value = "/User/info/{userId}")
    public AppUser infoUser(@PathVariable Long userId) {
        System.out.println("groupId:" + userId);
        AppUser newUser = userService.findUserById(userId);

        return newUser;
    }

    /**
     * Remove the user identified by its ID.
     *
     * @return true if the user existed and then is successfully removed, false otherwise.
     */
    @DeleteMapping(value = "/User/deleteByID/{userId}")
    public boolean deleteUserByID(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    /**
     * Remove the user identified by its username.
     *
     * @return true if the user existed and then is successfully removed, false otherwise.
     */
    @DeleteMapping(value = "/User/deleteByUsername/{userName}")
    public boolean deleteUserByUsername(@PathVariable String userName) {
        return userService.delete(userName);
    }


    //

    // populate db with default staffs

    //

    @GetMapping(value = "/User/createUser")
    public void createUsers() {
        //(String userName, String encrytedPassword, String userEmail, boolean enabled)
        AppUser user = new AppUser("dbuser1", encryptePassword("123"), "lol@lol.it", true);
        userService.save(user);
        user = new AppUser("dbadmin1", encryptePassword("123"), "lol@lolool.it", true);
        userService.save(user);
        user = new AppUser("ciao", encryptePassword("123"), "lol@lolool.it", true);
        userService.save(user);
        user = new AppUser("Luca", encryptePassword("Luca"), "luca@luca.com", true);
        userService.save(user);
        user = new AppUser("Davide", encryptePassword("Davide"), "davide@qualcuno.en", true);
        userService.save(user);
        user = new AppUser("lonevetad", encryptePassword("lonevetad"), "lone@vetad.it", true);
        userService.save(user);
        user = new AppUser("Bender", encryptePassword("im_drunk"), "im_drunk@beer.it", true);
        userService.save(user);
    }

    @PostMapping(value = "/User/createAll")
    public void createAllHomeData() {
        createUsers();
        // create role and RoleUser
    }


}
