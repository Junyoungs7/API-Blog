package com.jun.blog.weatherDomain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jun.blog.weatherDomain.dto.RegionRequestDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherResponseDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.FinalMinMaxDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherRequestDTO;
import com.jun.blog.weatherDomain.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/weathermain")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/main")
    public String weathersMain(Model model) {
        model.addAttribute("menu", "weather");
        model.addAttribute("RegionRequestDTO", new RegionRequestDTO());
        return "weather";
    }

    @PostMapping("/main")
    public String weeksWeather(@Valid WeeksWeatherRequestDTO requestDTO, BindingResult bindingResult, Model model) {

        log.info("request = {}", requestDTO);
        if(bindingResult.hasErrors()){
            return "weather";
        }

        try {
            List<FinalMinMaxDTO> finalMinMaxDTOList = weatherService.MvcService(requestDTO);
            log.info("response = {}", finalMinMaxDTOList.get(0));
            model.addAttribute("response", finalMinMaxDTOList);
            model.addAttribute("city", new WeeksWeatherRequestDTO());
            model.addAttribute("name", requestDTO.getCity());
            return "weather";
        } catch (JsonProcessingException e) {
            model.addAttribute("errorMessage", "지역을 다시 입력해주세요.");
            model.addAttribute("city", new WeeksWeatherRequestDTO());
            return "weather";
        }
    }

    @PostMapping("/main/select")
    public String weeksWeather(@ModelAttribute RegionRequestDTO requestDTO, BindingResult bindingResult, Model model) {

        log.info("request = {} {}", requestDTO.getCity1(), requestDTO.getCity2());
        if(bindingResult.hasErrors()){
            return "weather";
        }
        String name = requestDTO.getCity1() + requestDTO.getCity2();

        try {
            RegionWeatherResponseDTO responseDTO = weatherService.regionWeatherApi(requestDTO);
            log.info("response = {}", responseDTO.getTemperatureMinMax().get(0));
            model.addAttribute("response", responseDTO.getTemperatureMinMax());
            model.addAttribute("minmax", responseDTO.getTemperatureMinMax());
            model.addAttribute("city", new WeeksWeatherRequestDTO());
            model.addAttribute("name", name);
            return "weather";
        } catch (JsonProcessingException e) {
            model.addAttribute("errorMessage", "지역을 다시 입력해주세요.");
            model.addAttribute("city", new WeeksWeatherRequestDTO());
            return "weather";
        }
    }





}
