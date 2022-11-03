package com.jun.blog.weatherDomain.controller;

import com.jun.blog.weatherDomain.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherApiService weatherApiService;

    @GetMapping("/weathers")
    public String getWeather(){
        return weatherApiService.getApi();
    }
}
