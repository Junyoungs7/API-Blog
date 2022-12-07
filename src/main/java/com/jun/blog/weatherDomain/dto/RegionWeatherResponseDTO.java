package com.jun.blog.weatherDomain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class RegionWeatherResponseDTO {
    private List<ItemDTO> temperaturePerHour;
    private List<ItemDTO> temperatureMinMax;
}
