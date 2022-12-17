package com.jun.blog.jsonInDB.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.blog.jsonInDB.jsonDto.CodeDTO;
import com.jun.blog.jsonInDB.jsonDto.LocationDTO;
import com.jun.blog.weatherDomain.model.WeatherCodeEntity;
import com.jun.blog.weatherDomain.model.WeatherLocatinEntity;
import com.jun.blog.weatherDomain.repository.WeatherCodeRepository;
import com.jun.blog.weatherDomain.repository.WeatherLocationRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonService {

    private final WeatherLocationRepository weatherLocationRepository;
    private final WeatherCodeRepository weatherCodeRepository;

    public void locationService(String locationData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray(locationData);
        for (int i = 0; i < jsonArray.toList().size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            LocationDTO locationDTO = mapper.readValue(jsonObject.toString(), LocationDTO.class);
            WeatherLocatinEntity weatherLocatinEntity = WeatherLocatinEntity.builder()
                    .code(locationDTO.getCode())
                    .country(locationDTO.getCountry())
                    .city1(locationDTO.getCity1())
                    .city2(locationDTO.getCity2())
                    .city3(locationDTO.getCity3())
                    .x(locationDTO.getX())
                    .y(locationDTO.getY())
                    .longHour(locationDTO.getLongHour())
                    .longMinute(locationDTO.getLongMinute())
                    .longSecond(locationDTO.getLongSecond())
                    .latHour(locationDTO.getLatHour())
                    .latMinute(locationDTO.getLatMinute())
                    .latSecond(locationDTO.getLatSecond())
                    .longSecond100(locationDTO.getLongSecond100())
                    .latSecond100(locationDTO.getLatSecond100())
                    .locationUpdate(locationDTO.getLocationUpdate())
                    .build();
            weatherLocationRepository.save(weatherLocatinEntity);
            System.out.println(locationDTO.toString());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void codeService(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.toList().size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CodeDTO codeDTO = mapper.readValue(jsonObject.toString(), CodeDTO.class);
            WeatherCodeEntity weatherCodeEntity = WeatherCodeEntity.builder()
                    .city(codeDTO.getCity())
                    .code(codeDTO.getCode())
                    .build();
            weatherCodeRepository.save(weatherCodeEntity);
            System.out.println(jsonObject.toString());
            System.out.println(codeDTO.toString());
        }
    }
}
