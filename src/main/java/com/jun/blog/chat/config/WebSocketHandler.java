//package com.jun.blog.chat.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jun.blog.chat.dto.ChatMessage;
//import com.jun.blog.chat.dto.ChatRoom;
//import com.jun.blog.chat.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    // 채팅 1번
////    private static List<WebSocketSession> list = new ArrayList<>();
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
////        String payload = message.getPayload();
////        for (WebSocketSession webSocketSession : list) {
////            webSocketSession.sendMessage(message);
////        }
////
////    }
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
////        list.add(session);
////        log.info(session + " 클라이언트 접속");
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
////        log.info(session + "클라이언트 접속 해제");
////        list.remove(session);
////    }
//
//    // 채팅 2번
//
//    private final ObjectMapper mapper;
//    private final ChatService chatService;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
////        TextMessage textMessage = new TextMessage("welcome to my chatting server~");
////        session.sendMessage(textMessage);
//        ChatMessage chatMessage = mapper.readValue(payload, ChatMessage.class);
//        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
//        room.handleActions(session, chatMessage, chatService);
//    }
//
//}
