package com.jun.blog.weatherDomain.dto.weeksdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class FinalMinMaxDTO {

    private String min;
    private String max;
    private String day;
    private int count;
}
