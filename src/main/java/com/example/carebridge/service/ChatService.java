package com.example.carebridge.service;

import com.example.carebridge.entity.Message;
import com.example.carebridge.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 채팅 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional
public class ChatService {

    private final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final MessageRepository messageRepository;

    @Autowired
    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * 새로운 메시지 저장
     * @param message 저장할 메시지
     * @return 저장된 메시지
     */
    public Message saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        message.setReadStatus(false);
        return messageRepository.save(message);
    }

    /**
     * 읽지 않은 메시지 조회
     * @param userId 사용자 ID
     * @return 읽지 않은 메시지 목록
     */
    public List<Message> findUnreadMessages(Integer userId) {
        return messageRepository.findByReceiverIdAndReadStatusFalse(userId);
    }

    /**
     * 대화 이력 조회
     * @param conversationId 대화 ID
     * @return 대화 메시지 목록
     */
    public List<Message> getConversationHistory(Integer conversationId) {
        return messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
    }

    /**
     * 메시지 읽음 상태 업데이트
     * @param messageIds 메시지 ID 목록
     * @param userId 사용자 ID
     */
    public void markMessagesAsRead(List<Integer> messageIds, Integer userId) {
        List<Message> messages = messageRepository.findAllById(messageIds);

        for (Message message : messages) {
            // 수신자만 읽음 상태를 변경할 수 있도록 검증
            if (message.getReceiverId().equals(userId)) {
                message.setReadStatus(true);
            }
        }

        messageRepository.saveAll(messages);
    }
}