package com.jun.blog.weatherDomain.repository;

import com.jun.blog.weatherDomain.model.WeatherLocatinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLocationRepository extends JpaRepository<WeatherLocatinEntity, Long> {
}
