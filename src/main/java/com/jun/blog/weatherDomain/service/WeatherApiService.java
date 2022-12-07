package com.jun.blog.weatherDomain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.blog.weatherDomain.dto.daydto.ItemDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherRequestDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherResponseDTO;
import com.jun.blog.weatherDomain.dto.daydto.WeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WeatherApiService {
    @Value("${weather-apikey}")
    String serviceKey;
    String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    String BASE_WEEKS_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa";
    String pageNo = "1"; //fixed
    String numOfRows = "544"; //fixed
    String dataType = "JSON"; //fixed
    String base_time = "0200"; //fixed

    String tmFc = "202212070600";
    public WeatherApiDTO getApi(String base_date, String nx, String ny) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("dataType", dataType)
                        .queryParam("base_date", base_date)
                        .queryParam("base_time", base_time)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return mapper.readValue(result, WeatherApiDTO.class);


    }
    public WeatherApiDTO testApi() throws JsonProcessingException {
        LocalDate now = LocalDate.now();

        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 포맷 적용
        String base_date = now.format(formatter);
        log.info("base data = {}", base_date);
        String nx = "60";
        String ny = "120";
        return getApi(base_date, nx, ny);
    }

    public RegionWeatherResponseDTO regionWeatherApi(RegionWeatherRequestDTO requestDTO) throws JsonProcessingException {
        String base_date = requestDTO.getBase_date();
        String nx = requestDTO.getNx();
        String ny = requestDTO.getNy();
        WeatherApiDTO weatherApiDTO = getApi(base_date, nx, ny);
        List<ItemDTO> itemDTOS = weatherApiDTO.getResponse().getBody().getItems().getItem();
        List<ItemDTO> temperaturePerHour = new ArrayList<>();
        List<ItemDTO> temperatureMinMax = new ArrayList<>();

        for(ItemDTO itemDTO : itemDTOS){
            if (itemDTO.getCategory().equals("TMP")) {
                temperaturePerHour.add(itemDTO);
            } else if (itemDTO.getCategory().equals("TMN") || itemDTO.getCategory().equals("TMX")) {
                temperatureMinMax.add(itemDTO);
            }
        }

        return RegionWeatherResponseDTO.builder().temperatureMinMax(temperatureMinMax).temperaturePerHour(temperaturePerHour).build();
    }

    public WeeksWeatherApiDTO weeksWeatherApi(WeeksWeatherRequestDTO requestDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_WEEKS_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_WEEKS_URL).build();

        String result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", "10")
                        .queryParam("dataType", dataType)
                        .queryParam("regId", requestDTO.getRegId())
                        .queryParam("tmFc", tmFc)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return mapper.readValue(result, WeeksWeatherApiDTO.class);
    }





}
