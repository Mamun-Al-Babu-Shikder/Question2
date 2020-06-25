package com.example.demo.controller;

import com.example.demo.dao.AppUserRepository;
import com.example.demo.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

/**
 * Created by A.A.MAMUN on 6/25/2020.
 */
@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;

    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String loginPage(AppUser appUser){
        return "login";
    }

    @PostMapping("/login-process")
    public String loginProcess(@Valid AppUser appUser, Errors errors, Model model){
        if(errors.hasErrors()){
            return "login";
        }
        AppUser user = appUserRepository.findAppUserByEmail(appUser.getEmail());
        if(user==null || !appUser.getPassword().equals(user.getPassword())){
            return "redirect:/failed";
        }else{
            return "redirect:/success";
        }
    }

    @GetMapping("/success")
    public String loginSuccess(Model model){
        model.addAttribute("message","You have successfully logged to this demo project.");
        return "success";
    }

    @GetMapping("/failed")
    public String loginFailed(Model model){
        model.addAttribute("message","Login Failed! Please check your email address and password.");
        return "error";
    }


}
