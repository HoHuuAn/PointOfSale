package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class HomeController {







    @GetMapping(value = "/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username).orElseThrow();
        model.addAttribute("user", user);

        if(user.isFirstLogin()){
            return "redirect:/change-password-first-time";
        }
        return "index";
    }





    @Autowired
    UserRepository userRepository;


}
