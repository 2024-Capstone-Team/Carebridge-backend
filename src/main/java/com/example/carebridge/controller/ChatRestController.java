package com.example.carebridge.controller;

import com.example.carebridge.dto.MessageReadRequest;
import com.example.carebridge.entity.Message;
import com.example.carebridge.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채팅 관련 REST API를 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * 사용자의 읽지 않은 메시지 목록 조회
     * @param userId 사용자 ID
     * @return 읽지 않은 메시지 목록
     */
    @GetMapping("/messages/unread/{userId}")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Integer userId) {
        return ResponseEntity.ok(chatService.findUnreadMessages(userId));
    }

    /**
     * 특정 대화의 메시지 이력 조회
     * @param conversationId 대화 ID
     * @return 대화 메시지 목록
     */
    @GetMapping("/messages/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getConversationHistory(
            @PathVariable Integer conversationId) {
        return ResponseEntity.ok(chatService.getConversationHistory(conversationId));
    }

    /**
     * 메시지 읽음 상태 업데이트
     * @param request 읽음 처리 요청 정보
     * @return 처리 결과
     */
    @PutMapping("/messages/read")
    public ResponseEntity<Void> markMessagesAsRead(@RequestBody MessageReadRequest request) {
        chatService.markMessagesAsRead(request.getMessageIds(), request.getUserId());
        return ResponseEntity.ok().build();
    }
}