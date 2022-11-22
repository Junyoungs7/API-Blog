package com.jun.blog.weatherDomain.service;

import com.jun.blog.weatherDomain.dto.RegionWeatherRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class WeatherApiService {
    @Value("${weather-apikey}")
    String serviceKey;
    String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    String pageNo = "1"; //fixed
    String numOfRows = "290"; //fixed
    String dataType = "JSON"; //fixed
    String base_time = "2300"; //fixed
    public String getApi(String base_date, String nx, String ny){

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        return webClient.get()
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
    }
    public String testApi(){
        String base_date = "20221122";
        String nx = "55";
        String ny = "127";
        return getApi(base_date, nx, ny);
    }

    public String regionWeatherApi(RegionWeatherRequestDTO requestDTO){
        String base_date = requestDTO.getBase_date();
        String nx = requestDTO.getNx();
        String ny = requestDTO.getNy();

        return getApi(base_date, nx, ny);

    }

}
