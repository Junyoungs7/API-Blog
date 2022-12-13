package com.jun.blog.weatherDomain.dto.daydto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionWeatherRequestDTO {

    private String nx;
    private String ny;
    private String base_date;

}
