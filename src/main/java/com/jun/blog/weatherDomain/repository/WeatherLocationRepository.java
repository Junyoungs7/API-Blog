package com.jun.blog.weatherDomain.repository;

import com.jun.blog.weatherDomain.model.WeatherLocatinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLocationRepository extends JpaRepository<WeatherLocatinEntity, Long> {

    WeatherLocatinEntity findByCity1AndCity2AndCity3(String city1, String city2, String city3);
}
