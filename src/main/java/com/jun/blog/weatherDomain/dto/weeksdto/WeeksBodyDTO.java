package com.jun.blog.weatherDomain.dto.weeksdto;

import lombok.Getter;

@Getter
public class WeeksBodyDTO {
    private String dataType;
    private WeeksItemsDTO items;
    private String pageNo;
    private String numOfRows;
    private String totalCount;
}
