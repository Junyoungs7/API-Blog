package com.jun.blog.weatherDomain.dto.daydto;

import com.jun.blog.weatherDomain.dto.daydto.BodyDTO;
import com.jun.blog.weatherDomain.dto.daydto.HeaderDTO;
import lombok.Getter;

@Getter
public class ResponseDTO {

    private HeaderDTO header;
    private BodyDTO body;
}
