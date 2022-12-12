package com.jun.blog.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jun.blog.main.service.JsonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainApiController {

    private final JsonService jsonService;

    @PostMapping("/locationsave")
    public String jsonSave(@RequestBody String data) throws JsonProcessingException {
        jsonService.locationService(data);
        return null;
    }

    @PostMapping("/codesave")
    public String jsonSave2(@RequestBody String data) throws JsonProcessingException {
        jsonService.codeService(data);
        return null;
    }
}
