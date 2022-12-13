package com.jun.blog.weatherDomain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TodayCheckDTO {

    private String result;
    private List<String> weeks;

}
