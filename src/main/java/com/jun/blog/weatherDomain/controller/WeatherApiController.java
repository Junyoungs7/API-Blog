package com.jun.blog.weatherDomain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jun.blog.weatherDomain.dto.daydto.ItemDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherRequestDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherResponseDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksMinMaxDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherRequestDTO;
import com.jun.blog.weatherDomain.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherApiController {
    private final WeatherApiService weatherApiService;

    @GetMapping("/weathers")
    @ResponseBody
    public List<ItemDTO> getWeather() throws JsonProcessingException {
        log.info("responseDTO = {}", weatherApiService.testApi().getResponse().getBody().getItems().getItem().toString());
        List<ItemDTO> itemDTOS = weatherApiService.testApi().getResponse().getBody().getItems().getItem();
        return itemDTOS;
    }

    @PostMapping("/weathers")
    public ResponseEntity<?> regionWeather(@RequestBody RegionWeatherRequestDTO requestDTO){
        try{
            RegionWeatherResponseDTO responseDTO = weatherApiService.regionWeatherApi(requestDTO);
            log.info("response = {} {}", responseDTO.getTemperaturePerHour(), responseDTO.getTemperatureMinMax());
            return ResponseEntity.ok().body(responseDTO);
        }catch (NullPointerException e){
            log.error("지역 날씨 요청 에러",e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/weeks")
    public ResponseEntity<?> weeksWeather(@RequestBody WeeksWeatherRequestDTO requestDTO){
        try{
            WeeksMinMaxDTO responseDTO = weatherApiService.weeksWeatherApi(requestDTO);
            log.info("response = {}", responseDTO);
            return ResponseEntity.ok().body(responseDTO);
        }catch (NullPointerException e){
            log.error("지역 날씨 요청 에러",e);
            return new ResponseEntity<>("지역을 다시 입력해주세요.", HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
