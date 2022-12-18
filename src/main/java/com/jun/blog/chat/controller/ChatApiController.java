//package com.jun.blog.chat.controller;
//
//import com.jun.blog.chat.dto.ChatRoom;
//import com.jun.blog.chat.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat/api")
//public class ChatApiController {
//
//    private final ChatService chatService;
//    @GetMapping
//    public List<ChatRoom> findAllRoom(){
//        return chatService.findAllRoom();
//    }
//
//    @PostMapping
//    public ChatRoom chatRoom(@RequestParam String name){
//        return chatService.createRoom(name);
//    }
//}
