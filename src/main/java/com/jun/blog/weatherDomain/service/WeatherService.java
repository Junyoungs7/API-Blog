package com.jun.blog.weatherDomain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.blog.weatherDomain.dto.RegionRequestDTO;
import com.jun.blog.weatherDomain.dto.TodayCheckDTO;
import com.jun.blog.weatherDomain.dto.daydto.ItemDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherRequestDTO;
import com.jun.blog.weatherDomain.dto.daydto.RegionWeatherResponseDTO;
import com.jun.blog.weatherDomain.dto.daydto.WeatherApiDTO;
import com.jun.blog.weatherDomain.dto.weeksdto.*;
import com.jun.blog.weatherDomain.model.WeatherCodeEntity;
import com.jun.blog.weatherDomain.model.WeatherLocatinEntity;
import com.jun.blog.weatherDomain.repository.WeatherCodeRepository;
import com.jun.blog.weatherDomain.repository.WeatherLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    @Value("${weather-apikey}")
    String serviceKey;
    String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    String BASE_WEEKS_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa";
    String pageNo = "1"; //fixed
    String numOfRows = "544"; //fixed
    String dataType = "JSON"; //fixed
    String base_time = "0200"; //fixed

    private final WeatherCodeRepository weatherCodeRepository;
    private final WeatherLocationRepository weatherLocationRepository;

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

    public RegionWeatherRequestDTO checkRegionLocation(RegionRequestDTO requestDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String day = "";
        int hour = time.getHour();
        if (hour < 2) {
            LocalDate yesterday = date.minusDays(1);
            day = yesterday.format(formatter);
        }else{
            day = date.format(formatter);
        }
        String city3 = "";
        WeatherLocatinEntity weatherLocatinEntity = weatherLocationRepository.findByCity1AndCity2AndCity3(requestDTO.getCity1(), requestDTO.getCity2(), city3);
        log.info("weather = {} {} {}", weatherLocatinEntity.getX(), weatherLocatinEntity.getY(), day);
        return RegionWeatherRequestDTO.builder()
                .nx(weatherLocatinEntity.getX())
                .ny(weatherLocatinEntity.getY())
                .base_date(day)
                .build();
    }

    public RegionWeatherResponseDTO regionWeatherApi(RegionRequestDTO requestDTO) throws JsonProcessingException {
        RegionWeatherRequestDTO regionWeatherRequestDTO = checkRegionLocation(requestDTO);
        WeatherApiDTO weatherApiDTO = getApi(regionWeatherRequestDTO.getBase_date(), regionWeatherRequestDTO.getNx(), regionWeatherRequestDTO.getNy());
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

    public TodayCheckDTO checkCodeAndResult(WeeksWeatherRequestDTO requestDTO){
        //log.info(requestDTO.getCity());
        WeatherCodeEntity weatherCodeEntity = weatherCodeRepository.findByCity(requestDTO.getCity());


        //현재보다 하루 전 날짜로 검색
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate now = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        String tmFc = "";
        LocalDate data = LocalDate.now();
        DayOfWeek day = data.getDayOfWeek();
        int today = day.getValue();
        String[] weeksValue = {"월", "화", "수", "목","금", "토", "일"};
        List<String> weeks = new ArrayList<>();

        int hour = timeNow.getHour();
        if (hour < 6 && hour >= 0) {
            LocalDate yesterday = now.minusDays(1);
            today +=2;
            tmFc = yesterday.format(formatter)+"1800";
        } else if (hour >= 6 && hour < 18) {
            tmFc = now.format(formatter) + "0600";
            today+=3;
        } else if(hour >= 18 && hour < 24) {
            tmFc = now.format(formatter) + "1800";
            today+=3;
        }

        for (int i = 1; i < 9; i++) {
            if(today > 7){
                today -= 7;
            }
            weeks.add(weeksValue[today-1]);
            today++;
        }


        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_WEEKS_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_WEEKS_URL).build();

        String finalTmFc = tmFc;
        String result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", "10")
                        .queryParam("dataType", dataType)
                        .queryParam("regId", weatherCodeEntity.getCode())
                        .queryParam("tmFc", finalTmFc)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        TodayCheckDTO todayCheckDTO = TodayCheckDTO.builder().result(result).weeks(weeks).build();
        return todayCheckDTO;
    }

    public WeeksMinMaxDTO weeksWeatherApi(WeeksWeatherRequestDTO requestDTO) throws JsonProcessingException {

        TodayCheckDTO dto = checkCodeAndResult(requestDTO);
        String result = dto.getResult();
        ObjectMapper mapper = new ObjectMapper();
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

    public List<FinalMinMaxDTO> MvcService(WeeksWeatherRequestDTO requestDTO) throws JsonProcessingException {
        TodayCheckDTO dto = checkCodeAndResult(requestDTO);
        String result = dto.getResult();
        List<String> weeks = dto.getWeeks();
        ObjectMapper mapper = new ObjectMapper();


        //최저/최고 온도만 뽑기
        WeeksWeatherApiDTO weeksWeatherApiDTO = mapper.readValue(result, WeeksWeatherApiDTO.class);
        WeeksItemDTO weeksItemDTO = weeksWeatherApiDTO.getResponse().getBody().getItems().getItem().get(0);
        List<FinalMinMaxDTO> finalMinMaxDTOList = new ArrayList<>();

        FinalMinMaxDTO finalMinMaxDTO3 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin3()).max(weeksItemDTO.getTaMax3()).day(weeks.get(0)).count(3).build();
        finalMinMaxDTOList.add(finalMinMaxDTO3);
        FinalMinMaxDTO finalMinMaxDTO4 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin4()).max(weeksItemDTO.getTaMax4()).day(weeks.get(1)).count(4).build();
        finalMinMaxDTOList.add(finalMinMaxDTO4);
        FinalMinMaxDTO finalMinMaxDTO5 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin5()).max(weeksItemDTO.getTaMax5()).day(weeks.get(2)).count(5).build();
        finalMinMaxDTOList.add(finalMinMaxDTO5);
        FinalMinMaxDTO finalMinMaxDTO6 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin6()).max(weeksItemDTO.getTaMax6()).day(weeks.get(3)).count(6).build();
        finalMinMaxDTOList.add(finalMinMaxDTO6);
        FinalMinMaxDTO finalMinMaxDTO7 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin7()).max(weeksItemDTO.getTaMax7()).day(weeks.get(4)).count(7).build();
        finalMinMaxDTOList.add(finalMinMaxDTO7);
        FinalMinMaxDTO finalMinMaxDTO8 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin8()).max(weeksItemDTO.getTaMax8()).day(weeks.get(5)).count(8).build();
        finalMinMaxDTOList.add(finalMinMaxDTO8);
        FinalMinMaxDTO finalMinMaxDTO9 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin9()).max(weeksItemDTO.getTaMax9()).day(weeks.get(6)).count(9).build();
        finalMinMaxDTOList.add(finalMinMaxDTO9);
        FinalMinMaxDTO finalMinMaxDTO10 = FinalMinMaxDTO.builder().min(weeksItemDTO.getTaMin10()).max(weeksItemDTO.getTaMax10()).day(weeks.get(7)).count(10).build();
        finalMinMaxDTOList.add(finalMinMaxDTO10);

        return finalMinMaxDTOList;
    }

}
