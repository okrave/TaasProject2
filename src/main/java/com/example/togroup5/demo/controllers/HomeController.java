package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.payloadsResults.AppUserRegistration;
import com.example.togroup5.demo.servicies.UserService;
import com.example.togroup5.demo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {


    @Autowired
    UserService userService;

    @GetMapping(value="/")
    public String index(Model model){
        model.addAttribute("title","Welcome");
        model.addAttribute("message","This is welcome page!");
        return "Home";
    }

    @GetMapping(value="/userPage/{id}")
    public String user_page(@PathVariable(required = false) String id){
        System.out.println(id);
        return "user_page";
    }

    @GetMapping(value="/Home")
    public String home(){
        return "Home";
    }


    @GetMapping(value="/login")
    public String login(){
        return "login";
    }



    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";

    }


    @GetMapping(value="userPageJ2ee")
    public String userPageJ2ee(){
        return "user_page_j2ee";
    }

    @GetMapping(value = "/esplora")
    public String esplora(){
        return "esplora";
    }


    //Funzione get registration
    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, AppUserRegistration user){
        modelAndView.addObject("userRegistration",user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value="/custom_login")
    public String customLogin(ModelAndView modelAndView,BindingResult bindingResult, HttpServletRequest request){
        return "asd";
    }

    @PostMapping (value = "/registration")
    public String createUser(ModelAndView modelAndView, @Valid AppUserRegistration user, BindingResult bindingResult, HttpServletRequest request){
        //AppUser existUser = userService.findByUsername(user.getUserName());
        System.out.println("Dentro registration: "+ user);
        AppUser supportUser = userService.findByUsername(user.getUserName());
        if(supportUser != null){
            System.out.println("Ho trovato l'utente: "+ supportUser);
            modelAndView.addObject("Error", "Utente già presente");
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

// --------------------------------   Metodi non utilizzati che possono esserci utili ---------------------------------------------

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

}
