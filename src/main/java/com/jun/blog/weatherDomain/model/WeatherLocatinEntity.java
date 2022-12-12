package com.jun.blog.weatherDomain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "weatherlocation")
public class WeatherLocatinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String code;
    private String city1;
    private String city2;
    private String city3;
    private String x;
    private String y;
    private String longHour;
    private String longMinute;
    private String longSecond;
    private String latHour;
    private String latMinute;
    private String latSecond;
    private String longSecond100;
    private String latSecond100;
    private String locationUpdate;

}
