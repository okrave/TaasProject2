package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.AppUserRegistration;
import com.example.togroup5.demo.servicies.UserService;
import com.example.togroup5.demo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/")
    public String index(Model model){
        model.addAttribute("title","Welcome");
        model.addAttribute("message","This is welcome page!");
        return "index";
    }

    @GetMapping(value="/home")
    public String home(){
        return "home";
    }

    @GetMapping(value="/client_home")
    public String client_home(){
        return "client_home";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User loginedUser = (User)  ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo",userInfo);
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";

    }

    /*@GetMapping(value = "/logout")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }*/

    @GetMapping(value = "/userInfo")
    public String userInfo(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        System.out.println("loginUser:"+ loginedUser);
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    //Funzione get registration
    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, AppUserRegistration user){
        modelAndView.addObject("userRegistration",user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping (value = "/registration")
    public String createUser(ModelAndView modelAndView, @Valid AppUserRegistration user, BindingResult bindingResult, HttpServletRequest request){
        //AppUser existUser = userService.findByUsername(user.getUserName());
        System.out.println("Dentro registration: "+ user);
        AppUser supportUser = userService.findByUsername(user.getUserName());
        if(supportUser != null){
            System.out.println("Ho trovato l'utente: "+ supportUser);
            modelAndView.addObject("Error", "Utente già presente");
            System.out.println("commit inutile");
            return "login";
        }

        System.out.println("Non ho trovato nessun utente");
        supportUser = new AppUser(user.getUserName(),user.getPassword(),user.getEmail(),true);
        userService.save(supportUser);
        return "index";
    }


    @GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }



}
