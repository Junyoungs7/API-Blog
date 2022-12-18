//package com.jun.blog.chat.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jun.blog.chat.dto.ChatRoom;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.*;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ChatService {
//
//    private final ObjectMapper mapper;
//    private Map<String, ChatRoom> chatRooms;
//
//    @PostConstruct
//    private void init(){
//        chatRooms = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom(){
//        return new ArrayList<>(chatRooms.values());
//    }
//
//    public ChatRoom findRoomById(String roomId) {
//        return chatRooms.get(roomId);
//    }
//
//    public ChatRoom createRoom(String name) {
//        String randomId = UUID.randomUUID().toString();
//        ChatRoom chatRoom = ChatRoom.builder()
//                .name(name).roomId(randomId).build();
//        chatRooms.put(randomId, chatRoom);
//        return chatRoom;
//    }
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try {
//            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}
