package com.jun.blog.weatherDomain.dto.weeksdto;

import com.jun.blog.weatherDomain.dto.daydto.ItemDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class WeeksItemsDTO {

    private List<WeeksItemDTO> item;
}
