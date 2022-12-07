package com.jun.blog.weatherDomain.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String baseDate;

    @Column(nullable = false)
    private String baseTime;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String fcstDate;

    @Column(nullable = false)
    private String fcstTime;

    @Column(nullable = false)
    private String fcstValue;

    @Column(nullable = false)
    private String nx;

    @Column(nullable = false)
    private String ny;

    @Builder
    public WeatherEntity(String baseDate, String baseTime, String category, String fcstDate, String fcstTime, String fcstValue, String nx, String ny){
        this.baseTime = baseTime;
        this.baseDate = baseDate;
        this.category = category;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.fcstValue = fcstValue;
        this.nx = nx;
        this.ny = ny;
    }

}
