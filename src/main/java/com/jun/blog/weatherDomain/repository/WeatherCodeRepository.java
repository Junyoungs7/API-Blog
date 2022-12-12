package com.jun.blog.weatherDomain.repository;

import com.jun.blog.weatherDomain.model.WeatherCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherCodeRepository extends JpaRepository<WeatherCodeEntity, Long> {
    WeatherCodeEntity findByCity(String city);

}
