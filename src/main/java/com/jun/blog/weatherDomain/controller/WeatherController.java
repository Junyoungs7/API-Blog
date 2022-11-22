package com.jun.blog.weatherDomain.controller;

import com.jun.blog.weatherDomain.dto.RegionWeatherRequestDTO;
import com.jun.blog.weatherDomain.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherApiService weatherApiService;

    @GetMapping("/weathers")
    public String getWeather(){
        return weatherApiService.testApi();
    }
    //@ExceptionHandler(NoSuchElementException.class)
    @PostMapping("/weathers")
    public ResponseEntity<String> regionWeather(@RequestBody RegionWeatherRequestDTO requestDTO){
        try{
            log.info("requestDTO ={},{},{} ",requestDTO.getBase_date(),requestDTO.getNx(),requestDTO.getNy());
            //return ResponseEntity.ok(weatherApiService.regionWeatherApi(requestDTO)).getStatusCode();
            return new ResponseEntity<>(weatherApiService.regionWeatherApi(requestDTO), HttpStatus.OK);
            //return weatherApiService.regionWeatherApi(requestDTO);
        }catch (NullPointerException e){
            log.error("지역 날씨 요청 에러",e);
            return new ResponseEntity<>("잘못된 요청입니다. 다시 확인 부탁드립니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
