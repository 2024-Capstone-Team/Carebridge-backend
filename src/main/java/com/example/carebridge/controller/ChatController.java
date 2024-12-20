package com.example.carebridge.controller;

import com.example.carebridge.dto.ChatMessageDTO;
import com.example.carebridge.entity.Message;
import com.example.carebridge.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * WebSocket 통신 및 채팅 관련 요청을 처리하는 컨트롤러
 */
@Controller
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    /**
     * 클라이언트로부터 메시지를 받아 처리하고 수신자에게 전달
     * @param chatMessage 수신된 채팅 메시지
     * @param headerAccessor 메시지 헤더 정보
     */
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO chatMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        logger.info("메시지 수신 - 발신자: {}, 수신자: {}",
                chatMessage.getSenderId(),
                chatMessage.getReceiverId());

        try {
            // 메시지 저장 및 전송
            Message savedMessage = chatService.saveMessage(chatMessage.toEntity());

            // 수신자에게 메시지 전달
            messagingTemplate.convertAndSendToUser(
                    chatMessage.getReceiverId().toString(),
                    "/queue/messages",
                    savedMessage
            );

        } catch (Exception e) {
            logger.error("메시지 처리 중 오류 발생: ", e);
            // 발신자에게 에러 알림
            messagingTemplate.convertAndSendToUser(
                    chatMessage.getSenderId().toString(),
                    "/queue/errors",
                    "메시지 전송 실패"
            );
        }
    }
}