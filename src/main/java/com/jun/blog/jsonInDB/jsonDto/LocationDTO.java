package com.jun.blog.jsonInDB.jsonDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LocationDTO {

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
