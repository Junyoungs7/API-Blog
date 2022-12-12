package com.jun.blog.weatherDomain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.blog.weatherDomain.dto.daydto.ItemDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherRequestDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherResponseDTO;
import com.jun.blog.weatherDomain.dto.daydto.WeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksItemDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksMinMaxDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.WeeksWeatherRequestDTO;
import com.jun.blog.weatherDomain.model.WeatherCodeEntity;
import com.jun.blog.weatherDomain.repository.WeatherCodeRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WeatherApiService {
    @Value("${weather-apikey}")
    String serviceKey;
    String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    String BASE_WEEKS_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa";
    String pageNo = "1"; //fixed
    String numOfRows = "544"; //fixed
    String dataType = "JSON"; //fixed
    String base_time = "0200"; //fixed

    private final WeatherCodeRepository weatherCodeRepository;

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

    public WeeksMinMaxDTO weeksWeatherApi(WeeksWeatherRequestDTO requestDTO) throws JsonProcessingException {

        log.info(requestDTO.getCity());
        WeatherCodeEntity weatherCodeEntity = weatherCodeRepository.findByCity(requestDTO.getCity());

        ObjectMapper mapper = new ObjectMapper();

        //현재보다 하루 전 날짜로 검색
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String tmFc = yesterday.format(formatter)+"0600";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_WEEKS_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_WEEKS_URL).build();

        String result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", "10")
                        .queryParam("dataType", dataType)
                        .queryParam("regId", weatherCodeEntity.getCode())
                        .queryParam("tmFc", tmFc)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //최저/최고 온도만 뽑기
        WeeksWeatherApiDTO weeksWeatherApiDTO = mapper.readValue(result, WeeksWeatherApiDTO.class);
        WeeksItemDTO weeksItemDTO = weeksWeatherApiDTO.getResponse().getBody().getItems().getItem().get(0);
        return WeeksMinMaxDTO.builder()
                .taMin3(weeksItemDTO.getTaMin3())
                .taMax3(weeksItemDTO.getTaMax3())
                .taMin4(weeksItemDTO.getTaMin4())
                .taMax4(weeksItemDTO.getTaMax4())
                .taMin5(weeksItemDTO.getTaMin5())
                .taMax5(weeksItemDTO.getTaMax5())
                .taMin6(weeksItemDTO.getTaMin6())
                .taMax6(weeksItemDTO.getTaMax6())
                .taMin7(weeksItemDTO.getTaMin7())
                .taMax7(weeksItemDTO.getTaMax7())
                .taMin8(weeksItemDTO.getTaMin8())
                .taMax8(weeksItemDTO.getTaMax8())
                .taMin9(weeksItemDTO.getTaMin9())
                .taMax9(weeksItemDTO.getTaMax9())
                .taMin10(weeksItemDTO.getTaMin10())
                .taMax10(weeksItemDTO.getTaMax10())
                .build();
    }

}
