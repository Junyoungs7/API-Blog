package com.jun.blog.weatherDomain.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ItemsDTO {

    private List<ItemDTO> item;
}
