package com.jun.blog.weatherDomain.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemDTO {
    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private String nx;
    private String ny;
}
