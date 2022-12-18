package com.jun.blog.main.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("menu", "main");
        try {
            model.addAttribute("username", user.getUsername());
        } catch (Exception ignored) {
        }
        return "main";
    }
}
