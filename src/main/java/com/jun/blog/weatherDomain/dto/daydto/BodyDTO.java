package com.jun.blog.weatherDomain.dto.daydto;

import lombok.Getter;

@Getter
public class BodyDTO {
    private String dataType;
    private ItemsDTO items;
    private String pageNo;
    private String numOfRows;
    private String totalCount;
}
