package com.jun.blog.weatherDomain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weathermain")
public class WeatherController {

    @GetMapping("/main")
    public String weathersMain(Model model) {
        model.addAttribute("menu", "weather");
        return "weather";
    }


}
