package com.jun.blog.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ChatController {

    @GetMapping("/chat")
    public String chatGET(HttpSession session){
        log.info("@ChatController, chat GET()");
        return "chatting";
    }
}
